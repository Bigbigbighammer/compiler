package view;

import tax.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * 命令行交互界面类
 * 负责显示菜单和处理用户输入
 */
public class TaxConsoleMenu {
    private TaxCalculator calculator;
    private Scanner scanner;
    private TaxConfig taxConfig;


    public TaxConsoleMenu() {
        this.calculator = new TaxCalculator();
        this.scanner = new Scanner(System.in);
    }

    /**
     * 启动菜单的主循环
     */
    public void start() {
        boolean isRunning = true;

        String sourceType = "default";

        System.out.println("========================================");
        System.out.println("      个人所得税计算器 (PersonalTax)      ");
        System.out.println("========================================");

        initConfig(sourceType);

        while (isRunning) {
            showOptions();
            int choice = readIntInput();

            switch (choice) {
                case 1:
                    handleCalculateTax();
                    break;
                case 2:
                    handleSetThreshold();
                    break;
                case 3:
                    handleUpdateTaxTable();
                    break;
                case 4:
                    handleModifyRatesOnly();
                    break;
                case 0:
                    System.out.println("正在退出系统...");
                    isRunning = false;
                    break;
                default:
                    System.out.println(">> 错误：无效的选项，请输入 1-3。");
            }
            System.out.println(); // 打印空行美化布局
        }
        scanner.close();
    }

    private void initConfig(String sourceType) {
        TaxConfigLoader loader = TaxConfigLoaderFactory.getLoader(sourceType);
        try {
            taxConfig = loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 显示功能选项
     */
    private void showOptions() {
        System.out.println("\n当前起征点: " + taxConfig.getThreshold() + " 元");
        System.out.println("请选择功能:");
        System.out.println("  1. 计算个人所得税");
        System.out.println("  2. 设置起征点");
        System.out.println("  3. 重置整个税率表 (重新录入区间和税率)");
        System.out.println("  4. 仅调整税率 (保留区间不变)");
        System.out.println("  0. 退出");
        System.out.print("请输入选项序号: ");
    }

    /**
     * 处理计算功能的交互
     */
    private void handleCalculateTax() {
        System.out.print(">> 请输入您的月工资/薪金总额: ");
        double income = readDoubleInput();

        if (income < 0) {
            System.out.println(">> 错误：工资不能为负数。");
            return;
        }

        double tax = calculator.calculateTax(income, taxConfig);
        System.out.printf(">> 结果：您应缴纳的个人所得税为: %.2f 元\n", tax);
    }

    /**
     * 处理设置起征点的交互
     */
    private void handleSetThreshold() {
        System.out.print(">> 请输入新的起征点金额 (例如 1600 或 3500): ");
        double newThreshold = readDoubleInput();

        if (newThreshold < 0) {
            System.out.println(">> 错误：起征点不能为负数。");
            return;
        }

        taxConfig.setThreshold(newThreshold);
        System.out.println(">> 设置成功！当前起征点已更新为: " + newThreshold);
    }

    /**
     * 修改税率表的核心交互逻辑
     */
    private void handleUpdateTaxTable() {
        System.out.println("\n========== 修改税率表 ==========");
        System.out.println("说明：系统将引导您重新录入各级税率。");
        System.out.println("      上一级的【上限】将自动成为下一级的【下限】。");
        System.out.println("      输入金额 -1 代表“无穷大”（即最后一级）。");
        System.out.println("================================");

        List<TaxRule> newRules = new ArrayList<>();
        double currentMin = 0.0; // 第一级从 0 开始
        int grade = 1;
        boolean finished = false;

        while (!finished) {
            System.out.println("\n>> 正在录入第 [" + grade + "] 级税率规则");
            System.out.printf("   当前区间下限: %.0f 元\n", currentMin);

            // 1. 输入上限
            double currentMax = 0;
            while (true) {
                System.out.print("   请输入本级【上限金额】(输入 -1 代表无穷大/结束): ");
                double input = readDoubleInput(); // 之前封装好的读取方法

                if (input == -1) {
                    currentMax = Double.MAX_VALUE;
                    finished = true; // 这是最后一级
                    break;
                } else if (input <= currentMin) {
                    System.out.println("   错误：上限必须大于下限 (" + currentMin + ")，请重新输入。");
                } else {
                    currentMax = input;
                    break;
                }
            }

            // 2. 输入税率
            double currentRate = 0;
            while (true) {
                System.out.print("   请输入本级【税率百分比】(例如输入 5 代表 5%): ");
                double input = readDoubleInput();

                if (input < 0 || input > 100) {
                    System.out.println("   错误：税率必须在 0 到 100 之间。");
                } else {
                    currentRate = input / 100.0; // 转换为小数
                    break;
                }
            }

            // 3. 创建规则对象并添加
            TaxRule rule = new TaxRule(grade, currentMin, currentMax, currentRate);
            newRules.add(rule);

            // 4. 准备下一级
            if (!finished) {
                currentMin = currentMax; // 关键：当前上限变成下一级下限
                grade++;
            }
        }

        // 5. 保存更新
        taxConfig.getTaxTable().resetRules(newRules);
        System.out.println("\n>> 成功！税率表已更新。新的规则如下：");
        showCurrentTaxTable(); // 调用下面的显示方法
    }

    /**
     * 辅助方法：打印当前税率表
     */
    private void showCurrentTaxTable() {
        List<TaxRule> rules = taxConfig.getTaxTable().getTaxRulesList();
        if (rules.isEmpty()) {
            System.out.println("当前没有税率规则。");
            return;
        }
        System.out.println("--------------------------------------------------");
        for (TaxRule rule : rules) {
            System.out.println(rule.toString());
        }
        System.out.println("--------------------------------------------------");
    }

    /**
     * 仅修改各级税率（保留金额区间不变）
     */
    private void handleModifyRatesOnly() {
        // 1. 获取现有规则
        List<TaxRule> rules = taxConfig.getTaxTable().getTaxRulesList();

        if (rules.isEmpty()) {
            System.out.println(">> 错误：当前没有任何税率规则，请先初始化税率表。");
            return;
        }

        System.out.println("\n========== 调整各级税率 ==========");
        System.out.println("说明：系统将逐级显示当前区间，请输入新的税率百分比。");
        System.out.println("      (例如：原税率 5%，输入 10 代表改为 10%)");
        System.out.println("================================");

        // 2. 遍历修改
        for (TaxRule rule : rules) {
            System.out.println("\n>> 正在修改第 [" + rule.getGrade() + "] 级");

            // 打印友好的提示信息，显示当前区间
            String rangeDesc;
            if (rule.getMax() == Double.MAX_VALUE) {
                rangeDesc = String.format("超过 %.0f 元的部分", rule.getMin());
            } else {
                rangeDesc = String.format("%.0f 元 至 %.0f 元", rule.getMin(), rule.getMax());
            }
            System.out.println("   区间范围: " + rangeDesc);
            System.out.printf("   当前税率: %.0f%%\n", rule.getRate() * 100);

            // 3. 读取新税率
            double newRatePercent = 0;
            while (true) {
                System.out.print("   请输入新税率 (0-100): ");
                double input = readDoubleInput(); // 使用之前封装好的读取方法

                if (input < 0 || input > 100) {
                    System.out.println("   错误：税率必须在 0 到 100 之间，请重新输入。");
                } else {
                    newRatePercent = input;
                    break;
                }
            }

            // 4. 执行更新 (将百分比转换为小数，如 10 -> 0.1)
            rule.setRate(newRatePercent / 100.0);
            System.out.println("   --> 已更新为 " + newRatePercent + "%");
        }

        System.out.println("\n>> 所有级别的税率已更新完毕！");
        showCurrentTaxTable(); // 显示最终结果
    }

    /**
     * 辅助方法：读取整数输入，并处理非数字异常
     * 符合实验评价 2.3 关于健壮性的要求
     */
    private int readIntInput() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next(); // 清空错误的输入缓存
            return -1; // 返回一个无效值
        }
    }

    /**
     * 辅助方法：读取浮点数输入，并处理非数字异常
     */
    private double readDoubleInput() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            scanner.next(); // 清空错误的输入缓存
            return -1.0;
        }
    }
}
