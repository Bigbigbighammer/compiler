package model.loader;

import model.entity.TaxConfig;

public interface TaxConfigLoader {
    /**
     * 加载税收配置
     * @return TaxConfig 对象
     * @throws Exception 加载失败时抛出异常
     */
    TaxConfig load() throws Exception;
}