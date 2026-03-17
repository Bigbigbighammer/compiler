package service;

import model.entity.TaxConfig;
import model.entity.TaxRule;
import model.entity.TaxTable;

/**
 * 税收计算器
 *
 * 用于根据配置的税率表计算个人所得税。该类实现了累进税率的计算逻辑，
 * 支持不同的起征点和多条税率规则。
 *
 * <h2>计算流程</h2>
 * <ol>
 *   <li>获取总收入</li>
 *   <li>扣除起征点（免税额）</li>
 *   <li>对超出部分按税率表逐级计算税费</li>
 *   <li>返回总税费</li>
 * </ol>
 *
 * <h2>计算公式示例</h2>
 * <pre>
 * 假设起征点为1600元，税率表为标准5级
 *
 * 月工资2000元：
 *   超出额 = 2000 - 1600 = 400元
 *   应缴税 = 400 × 5% = 20元
 *
 * 月工资3000元：
 *   超出额 = 3000 - 1600 = 1400元
 *   应缴税 = 500 × 5% + 900 × 10% = 25 + 90 = 115元
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>
 * TaxConfigLoader loader = new JsonFileTaxConfigLoader("settings.json");
 * TaxConfig config = loader.load();
 * TaxCalculator calculator = new TaxCalculator(config);
 *
 * double salary = 2000;
 * double tax = calculator.calculateTax(salary);
 * System.out.println("应缴税: " + tax + " 元");
 * </pre>
 *
 * @author Aaron
 * @version 1.0.0
 * @see TaxConfig
 * @see TaxTable
 * @see BaseTaxHandler
 * @see TaxChain
 */
public class TaxCalculator {

    /** 税收配置对象 */
    private TaxConfig taxConfig;

    /**
     * 构造函数
     *
     * @param taxConfig 税收配置对象，包含起征点和税率表
     */
    public TaxCalculator(TaxConfig taxConfig) {
        this.taxConfig = taxConfig;
    }

    /**
     * 获取当前的税收配置
     *
     * @return TaxConfig对象
     */
    public TaxConfig getTaxConfig() {
        return taxConfig;
    }

    /**
     * 设置新的税收配置
     *
     * @param taxConfig 新的TaxConfig对象
     */
    public void setTaxConfig(TaxConfig taxConfig) {
        this.taxConfig = taxConfig;
    }

    /**
     * 计算个人所得税
     *
     * <p>根据配置的起征点和税率表，计算月工资对应的所得税。
     * 如果月工资低于起征点，返回0。</p>
     *
     * <p>计算步骤：</p>
     * <ol>
     *   <li>获取超出起征点的部分：salaryBeyondThreshold = salary - threshold</li>
     *   <li>如果超出额小于0，直接返回0</li>
     *   <li>否则，通过税率链进行逐级计算</li>
     * </ol>
     *
     * @param salary 月工资金额（元）
     * @return 应缴个人所得税（元）
     * @throws RuntimeException 当计算过程中出现错误时抛出
     *
     * @see #buildTaxChain(TaxConfig)
     */
    public double calculateTax(double salary) {
        if (salary < 0) {
            throw new RuntimeException("Salary cannot be negative: " + salary);
        }
        if (taxConfig == null) {
            throw new RuntimeException("Tax configuration is not initialized");
        }

        try {
            TaxChain taxChain = buildTaxChain(taxConfig);
            double salaryBeyondThreshold = salary - taxConfig.getThreshold();
            if (salaryBeyondThreshold < 0) {
                return 0;
            }
            return taxChain.calculate(salaryBeyondThreshold);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to calculate tax: " + e.getMessage(), e);
        }
    }

    /**
     * 构建税率处理链
     *
     * <p>根据配置的税率表，创建一个由多个TaxHandler组成的处理链。
     * 每个处理器负责处理相应等级的税率计算。</p>
     *
     * @param taxConfig 税收配置对象
     * @return 构建好的TaxChain对象
     * @throws RuntimeException 当配置无效时抛出异常
     *
     * @see TaxChain
     * @see BaseTaxHandler
     */
    private TaxChain buildTaxChain(TaxConfig taxConfig) {
        if (taxConfig == null || taxConfig.getTaxTable() == null) {
            throw new RuntimeException("Invalid tax configuration");
        }

        try {
            TaxChain taxChain = new TaxChain();
            TaxTable taxTable = taxConfig.getTaxTable();

            if (taxTable.getTaxRulesList() == null || taxTable.getTaxRulesList().isEmpty()) {
                throw new RuntimeException("Tax rules list is empty");
            }

            for (TaxRule taxRule : taxTable.getTaxRulesList()) {
                if (taxRule == null) {
                    throw new RuntimeException("Tax rule cannot be null");
                }
                taxChain.addLastHandler(new BaseTaxHandler(taxRule));
            }
            return taxChain;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to build tax chain: " + e.getMessage(), e);
        }
    }

}
