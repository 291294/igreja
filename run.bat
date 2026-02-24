@echo off
REM Script de inicialização da aplicação Sistema Igreja

cls
echo =========================================
echo    Sistema de Gerenciamento para Igrejas
echo =========================================
echo.

REM Verificar se está na pasta correta
if not exist "pom.xml" (
    echo Erro: pom.xml nao encontrado!
    echo Execute esse script na raiz do projeto.
    pause
    exit /b 1
)

echo Compilando projeto...
call mvn clean package -DskipTests -q

REM Verificar compilação
if errorlevel 1 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo ✓ Compilacao concluida com sucesso!
echo.
echo Para iniciar a aplicacao, execute:
echo   mvn spring-boot:run
echo.
echo Aguardando... (pressione qualquer tecla para continuar)
pause
