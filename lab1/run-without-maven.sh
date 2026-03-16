#!/bin/bash
# ============================================================================
# 个人所得税计算系统 - 无 Maven 启动脚本 (Linux/Mac)
# 该脚本用于没有 Maven 环境的情况下启动项目和执行单元测试
# 需要预先编译和打包好的 JAR 文件
# ============================================================================

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo ""
echo "============================================================================"
echo "个人所得税计算系统 - 无 Maven 版本启动脚本"
echo "============================================================================"
echo ""

# 检查 Java 是否安装
if ! command -v java &> /dev/null; then
    echo -e "${RED}[错误] Java 未找到或未在 PATH 中配置${NC}"
    echo "请确保已安装 JDK 8+ 并配置了环境变量"
    exit 1
fi

echo -e "${GREEN}[信息] Java 版本检查成功${NC}"
java -version
echo ""

# 菜单函数
show_menu() {
    echo "============================================================================"
    echo "请选择操作："
    echo "============================================================================"
    echo "1. 启动应用程序 (需要 JAR 文件)"
    echo "2. 执行单元测试 (需要编译的测试文件)"
    echo "3. 查看帮助信息"
    echo "4. 退出"
    echo "============================================================================"
}

# 主循环
while true; do
    show_menu
    read -p "请输入选项 [1-4]: " choice

    case $choice in
        1)
            echo ""
            echo -e "${YELLOW}[执行] 启动应用程序...${NC}"

            # 检查是否存在 JAR 文件
            if [ -f "target/tax-calculator.jar" ]; then
                echo -e "${YELLOW}[信息] 使用 JAR 文件启动...${NC}"
                java -jar target/tax-calculator.jar
            elif [ -d "target/classes" ]; then
                echo -e "${YELLOW}[信息] 使用编译的类文件启动...${NC}"
                java -cp "target/classes:." Main
            else
                echo -e "${RED}[错误] 找不到可执行文件${NC}"
                echo "请确保已经过编译（target/classes 存在）或已打包（target/tax-calculator.jar 存在）"
                echo ""
                echo "如果没有编译，请进行如下操作："
                echo "1. 在有 Maven 环境的机器上执行：mvn clean package"
                echo "2. 将整个项目文件夹复制到此机器上"
                echo "3. 再次运行此脚本"
            fi
            read -p "按 Enter 继续..."
            ;;
        2)
            echo ""
            echo -e "${YELLOW}[执行] 执行单元测试...${NC}"

            # 检查是否存在测试类
            if [ ! -d "target/test-classes" ]; then
                echo -e "${RED}[错误] 找不到编译的测试文件${NC}"
                echo "测试文件位置：target/test-classes"
                echo ""
                echo "请进行如下操作："
                echo "1. 在有 Maven 环境的机器上执行：mvn clean test"
                echo "2. 将整个项目文件夹复制到此机器上"
                echo "3. 再次运行此脚本"
            else
                echo -e "${YELLOW}[信息] 找到测试文件，但需要 JUnit 运行器${NC}"
                echo ""
                echo "说明："
                echo "- 使用 Maven 的 mvn test 命令是推荐的测试方式"
                echo "- 在没有 Maven 环境下，需要手动配置 classpath 来运行 JUnit 测试"
                echo "- 这需要所有依赖的 JAR 文件都在 classpath 中"
                echo ""
                echo "如果要在此环境运行测试，请："
                echo "1. 在有 Maven 环境的机器上执行：mvn clean test"
                echo "2. 查看 target/surefire-reports 目录中的测试报告"
            fi
            read -p "按 Enter 继续..."
            ;;
        3)
            echo ""
            echo "============================================================================"
            echo "帮助信息"
            echo "============================================================================"
            echo ""
            echo "本脚本用于在没有 Maven 环境的情况下运行已编译的项目。"
            echo ""
            echo "前置条件："
            echo "- 需要安装 JDK 8 或更高版本"
            echo "- 需要预先在有 Maven 环境的机器上编译和打包项目"
            echo ""
            echo "使用步骤："
            echo "1. 在有 Maven 环境的机器上："
            echo "   - 执行 mvn clean install （编译和安装）"
            echo "   - 执行 mvn clean package  （打包为 JAR 文件）"
            echo "   - 执行 mvn clean test     （运行单元测试）"
            echo ""
            echo "2. 将编译后的项目文件复制到此机器上"
            echo ""
            echo "3. 运行此脚本选择相应操作"
            echo ""
            echo "目录结构说明："
            echo "- target/classes/       编译后的应用程序类文件"
            echo "- target/test-classes/  编译后的测试类文件"
            echo "- target/lib/           依赖的 JAR 文件"
            echo "- target/surefire-reports/ 测试报告"
            echo ""
            read -p "按 Enter 继续..."
            ;;
        4)
            echo ""
            echo -e "${GREEN}[信息] 退出脚本${NC}"
            exit 0
            ;;
        *)
            echo -e "${RED}[错误] 无效的选项，请重新输入${NC}"
            read -p "按 Enter 继续..."
            ;;
    esac
done

