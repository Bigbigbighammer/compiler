package view;

import model.entity.TaxConfig;
import model.entity.TaxTable;
import model.loader.TaxConfigLoader;
import model.loader.TaxConfigLoaderFactory;
import model.loader.TaxConfigPersister;
import model.entity.TaxRule;
import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * 个人所得税计算系统的控制台交互界面
 *
 * <p>提供友好的命令行用户界面，用户可以通过菜单操作进行以下功能：
 * <ul>
 *   <li>计算个人所得税</li>
 *   <li>设置新的起征点</li>
 *   <li>重置整个税率表</li>
 *   <li>调整税率（保留区间不变）</li>
 * </ul>
 * </p>
 *
 * <h3>主要职责</h3>
 * <ul>
 *   <li>初始化税收配置</li>
 *   <li>显示菜单选项</li>
 *   <li>读取和验证用户输入</li>
 *   <li>调用相应的业务逻辑处理用户操作</li>
 *   <li>显示计算结果或操作结果</li>
 * </ul>
 *
 * <h3>使用示例</h3>
 * <pre>
 * // 启动程序
 * public static void main(String[] args) {
 *     new TaxConsoleMenu().start();
 * }
 *
 * // 然后根据菜单提示进行操作
 * </pre>
 *
 * <h3>菜单功能说明</h3>
 * <table border="1">
 *   <tr><th>选项</th><th>功能</th><th>说明</th></tr>
 *   <tr><td>配置源</td><td>选择配置源</td><td>选择使用默认配置还是JSON文件配置</td></tr>
 *   <tr><td>1</td><td>计算个人所得税</td><td>输入月工资，计算应缴税费</td></tr>
 *   <tr><td>2</td><td>设置起征点</td><td>修改个人所得税的免税额</td></tr>
 *   <tr><td>3</td><td>重置整个税率表</td><td>完全重新输入所有税率规则</td></tr>
 *   <tr><td>4</td><td>调整税率</td><td>仅修改税率，保留收入区间</td></tr>
 *   <tr><td>0</td><td>退出</td><td>关闭程序</td></tr>
 * </table>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxConfig
 * @see TaxConfigLoader
 * @see TaxCalculator
 */
public class TaxConsoleMenu {

    /** 控制台输入扫描器 */
    private Scanner scanner;

    /** 当前加载的税收配置 */
    private TaxConfig taxConfig;

    /** 配置源类型（"default" 或 "json"）*/
    private String configSourceType;


    /**
     * 构造函数
     * 初始化控制台菜单
     */
    public TaxConsoleMenu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * 启动菜单的主循环
     *
     * <p>显示欢迎界面，加载配置，然后进入交互循环。
     * 用户可以通过菜单选项进行各种操作。</p>
     */
    public void start() {
        boolean isRunning = true;

        System.out.println("========================================");
        System.out.println("      个人所得税计算器 (PersonalTax)      ");
        System.out.println("========================================");

        // 配置源选择菜单
        configSourceType = selectConfigSource();

        initConfig(configSourceType);

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
                    System.out.println(">> 错误：无效的选项，请输入 0-4。");
            }
            System.out.println(); // 打印空行美化布局
        }
        scanner.close();
    }

    /**
     * 让用户选择配置源类型
     *
     * <p>显示配置源选择菜单，用户可以选择使用以下配置来源：
     * <ul>
     *   <li>默认配置（硬编码的标准税率表）</li>
     *   <li>JSON文件配置（从settings.json加载）</li>
     * </ul>
     * </p>
     *
     * @return 配置源类型字符串："default" 或 "json"
     * @see TaxConfigLoaderFactory
     */
    private String selectConfigSource() {
        System.out.println("\n请选择配置源:");
        System.out.println("  1. 使用默认配置（硬编码的标准税率表）");
        System.out.println("  2. 使用JSON文件配置（从settings.json加载）");
        System.out.print("请输入选项 (1 或 2): ");

        int choice = readIntInput();

        switch (choice) {
            case 1:
                System.out.println("✓ 已选择默认配置");
                return "default";
            case 2:
                System.out.println("✓ 已选择JSON文件配置");
                return "json";
            default:
                System.out.println(">> 错误：无效的选项，使用默认配置");
                return "default";
        }
    }

    /**
     * 初始化税收配置
     *
     * <p>根据指定的配置源类型，使用工厂模式创建相应的加载器，
     * 加载税收配置。</p>
     *
     * @param sourceType 配置源类型（如："default"、"json"等）
     * @throws RuntimeException 当配置加载失败时抛出异常
     *
     * @see TaxConfigLoaderFactory
     */
    private void initConfig(String sourceType) {
        TaxConfigLoader loader = TaxConfigLoaderFactory.getLoader(sourceType);
        try {
            taxConfig = loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 显示功能菜单选项
     *
     * <p>显示当前的起征点和可用的功能选项，提示用户输入选择。</p>
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
     * 处理税费计算功能
     *
     * <p>与用户交互进行以下操作：
     * <ol>
     *   <li>读取用户输入的月工资</li>
     *   <li>验证输入的有效性</li>
     *   <li>调用TaxCalculator计算应缴税</li>
     *   <li>显示计算结果</li>
     * </ol>
     * </p>
     */
    private void handleCalculateTax() {
        System.out.print(">> 请输入您的月工资/薪金总额: ");
        double income = readDoubleInput();

        if (income < 0) {
            System.out.println(">> 错误：工资不能为负数。");
            return;
        }
        TaxCalculator calculator = new TaxCalculator(taxConfig);
        double tax = calculator.calculateTax(income);
        System.out.printf(">> 结果：您应缴纳的个人所得税为: %.2f 元\n", tax);
    }

    /**
     * 处理设置起征点的交互
     *
     * <p>与用户交互进行以下操作：
     * <ol>
     *   <li>读取用户输入的新起征点金额</li>
     *   <li>验证输入的有效性（不能为负）</li>
     *   <li>更新配置中的起征点</li>
     *   <li>显示成功提示</li>
     * </ol>
     * </p>
     *
     * @see TaxConfig#setThreshold(double)
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

        // 如果使用JSON配置，自动保存
        if ("json".equals(configSourceType)) {
            saveConfig();
        }
    }

    /**
     * 修改税率表的核心交互逻辑
     *
     * <p>与用户进行逐级交互，完全重新输入所有税率规则。主要特点：
     * <ul>
     *   <li>上一级的【上限】自动成为下一级的【下限】，保证连续性</li>
     *   <li>输入 -1 代表"无穷大"，用于最后一级</li>
     *   <li>支持任意数量的税率等级</li>
     *   <li>完成后显示更新后的税率表</li>
     * </ul>
     * </p>
     *
     * <h3>交互流程</h3>
     * <ol>
     *   <li>显示说明信息</li>
     *   <li>逐级输入税率规则（上限和税率）</li>
     *   <li>当用户输入 -1 时，标记为最后一级并结束录入</li>
     *   <li>将新规则保存到配置</li>
     *   <li>显示更新后的税率表</li>
     * </ol>
     * </p>
     *
     * @see TaxRule
     * @see TaxTable#resetRules(java.util.List)
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

        // 如果使用JSON配置，自动保存
        if ("json".equals(configSourceType)) {
            saveConfig();
        }
    }

    /**
     * 显示当前的税率表
     *
     * <p>以格式化的方式输出所有税率规则，包括：
     * <ul>
     *   <li>税率等级</li>
     *   <li>收入范围（"X元至Y元"或"超过X元的部分"）</li>
     *   <li>对应的税率百分比</li>
     * </ul>
     * </p>
     *
     * @see TaxRule#toString()
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
     * 保存配置到JSON文件
     *
     * <p>当用户使用JSON配置模式时，每次修改配置后都会自动调用此方法
     * 将修改内容保存到settings.json文件中。</p>
     *
     * <h3>保存内容</h3>
     * <ul>
     *   <li>起征点</li>
     *   <li>所有税率规则（等级、收入范围、税率）</li>
     * </ul>
     * </p>
     *
     * @see TaxConfigPersister
     * @see TaxConfigLoaderFactory#getDefaultPersister()
     */
    private void saveConfig() {
        try {
            TaxConfigPersister persister = TaxConfigLoaderFactory.getDefaultPersister();
            persister.save(taxConfig);
            System.out.println("✓ 配置已自动保存到 settings.json");
        } catch (Exception e) {
            System.out.println("✗ 配置保存失败: " + e.getMessage());
        }
    }

    /**
     * 仅修改各级税率（保留收入区间不变）
     *
     * <p>允许用户只修改税率数值，而不改变收入区间的定义。
     * 适用于在已有税率框架基础上进行微调的场景。</p>
     *
     * <h3>交互流程</h3>
     * <ol>
     *   <li>获取当前所有税率规则</li>
     *   <li>逐级显示当前收入区间和现有税率</li>
     *   <li>用户输入新的税率百分比</li>
     *   <li>更新规则的税率</li>
     *   <li>显示最终的税率表</li>
     * </ol>
     * </p>
     *
     * @see TaxRule#setRate(double)
     * @see #showCurrentTaxTable()
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

        // 如果使用JSON配置，自动保存
        if ("json".equals(configSourceType)) {
            saveConfig();
        }
    }

    /**
     * 读取整数输入并处理异常
     *
     * <p>从控制台安全地读取整数输入。当用户输入非数字内容时，
     * 自动清空缓冲区并返回 -1，表示输入错误。
     * 这个设计符合健壮性和容错性的要求。</p>
     *
     * @return 用户输入的整数，或 -1（表示输入异常）
     * @see InputMismatchException
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
     * 读取浮点数输入并处理异常
     *
     * <p>从控制台安全地读取浮点数输入。当用户输入非数字内容时，
     * 自动清空缓冲区并返回 -1.0，表示输入错误。
     * 这个设计符合健壮性和容错性的要求。</p>
     *
     * @return 用户输入的浮点数，或 -1.0（表示输入异常）
     * @see InputMismatchException
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
