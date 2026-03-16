#!/bin/bash

done
    esac
            ;;
            read -p "按 Enter 继续..."
            echo -e "${RED}[错误] 无效的选项，请重新输入${NC}"
        *)
            ;;
            exit 0
            echo -e "${GREEN}[信息] 退出脚本${NC}"
            echo ""
        7)
            ;;
            read -p "按 Enter 继续..."
            echo -e "${GREEN}[成功] 清理完成${NC}"
            mvn clean
            echo -e "${YELLOW}[执行] 清理构建目录...${NC}"
            echo ""
        6)
            ;;
            read -p "按 Enter 继续..."
            fi
                echo -e "${RED}[错误] JAR 文件生成失败${NC}"
            else
                java -cp "target/tax-calculator.jar:target/classes:." Main
            if [ -f "target/tax-calculator.jar" ]; then
            fi
                mvn clean package -DskipTests
                echo -e "${YELLOW}[信息] JAR 文件不存在，正在进行打包...${NC}"
            if [ ! -f "target/tax-calculator.jar" ]; then
            echo -e "${YELLOW}[执行] 启动应用程序...${NC}"
            echo ""
        5)
            ;;
            read -p "按 Enter 继续..."
            echo -e "${GREEN}[成功] 打包完成，生成文件：target/tax-calculator.jar${NC}"
            fi
                continue
                read -p "按 Enter 继续..."
                echo -e "${RED}[错误] 打包失败${NC}"
            if [ $? -ne 0 ]; then
            mvn clean package
            echo -e "${YELLOW}[执行] 打包项目...${NC}"
            echo ""
        4)
            ;;
            read -p "按 Enter 继续..."
            mvn clean install
            echo -e "${YELLOW}[执行] 清理、编译和测试...${NC}"
            echo ""
        3)
            ;;
            read -p "按 Enter 继续..."
            mvn test
            echo -e "${YELLOW}[执行] 执行所有单元测试...${NC}"
            echo ""
        2)
            ;;
            read -p "按 Enter 继续..."
            echo -e "${GREEN}[成功] 编译完成${NC}"
            fi
                continue
                read -p "按 Enter 继续..."
                echo -e "${RED}[错误] 编译失败${NC}"
            if [ $? -ne 0 ]; then
            mvn clean install -DskipTests
            echo -e "${YELLOW}[执行] 清理项目并编译...${NC}"
            echo ""
        1)
    case $choice in

    read -p "请输入选项 [1-7]: " choice
    show_menu
while true; do
# 主循环

}
    echo "============================================================================"
    echo "7. 退出"
    echo "6. 清理构建目录 (clean)"
    echo "5. 启动应用程序"
    echo "4. 打包项目 (package)"
    echo "3. 清理 + 编译 + 测试 (clean install)"
    echo "2. 执行所有单元测试 (test)"
    echo "1. 清理项目并编译 (clean install)"
    echo "============================================================================"
    echo "请选择操作："
    echo "============================================================================"
show_menu() {
# 菜单函数

echo ""
mvn --version
echo -e "${GREEN}[信息] Maven 版本检查成功${NC}"

fi
    exit 1
    echo "请确保已安装 Maven 并配置了环境变量"
    echo -e "${RED}[错误] Maven 未找到或未在 PATH 中配置${NC}"
if ! command -v mvn &> /dev/null; then
# 检查 Maven 是否安装

fi
    exit 1
    echo -e "${RED}[错误] 找不到 pom.xml 文件，请确保在项目根目录下运行此脚本${NC}"
if [ ! -f "pom.xml" ]; then
# 检查是否存在 pom.xml

echo ""
echo "============================================================================"
echo "个人所得税计算系统 - Maven 版本启动脚本"
echo "============================================================================"
echo ""

NC='\033[0m' # No Color
YELLOW='\033[1;33m'
GREEN='\033[0;32m'
RED='\033[0;31m'
# 设置颜色输出

# ============================================================================
# 该脚本用于有 Maven 环境的情况下启动项目和执行单元测试
# 个人所得税计算系统 - Maven 启动脚本 (Linux/Mac)
# ============================================================================
