# Visualizador_de_Tarefas

# Para executar:
 No arquivo TaskList.java presente no diretório 'src', faz-se necessário ajustar a String do System.load() para o diretório onde salvou o projeto
 ex:
 C:\Downloads\Gerenciador_de_Tarefas\src\getOSTaskList.dll
 
# Para compilar uma nova DLL:
 Passo-a-passo seguido: https://www.baeldung.com/jni
  1. Baixar o compilador GCC (compilador C)
  2. Na pasta onde estiver o arquivo TaskList.java, executar os seguintes comandos
     2.1. Criar um arquivo .h (C) com o método nativo criado em Java (o arquivo .h é uma interface):
     javac -h . TaskList.java
     2.2. Criando a DLL:
     gcc -c -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" TaskList.cpp -o TaskList.o
     gcc -shared -o getOSTaskList.dll TaskList.o -Wl,--add-stdcall-alias -lPsapi -lstdc++
 
# Links:
  1. passo-a-passo para gerar a DLL: https://www.baeldung.com/jni
  2. Código C++ inserido na DLL para retornar as informações dos processos direto pelo SO: https://docs.microsoft.com/pt-br/windows/win32/psapi/enumerating-all-processes
  3. Descrição das informações listadas no projeto: https://docs.microsoft.com/en-us/windows/win32/api/psapi/ns-psapi-process_memory_counters

# Ferramentas:
  1. Java (JDK 15.0.2)
  2. Eclipse: 2022-03 (Para o Java)
  3. Visual Studio Code (Para C/C++)
  4. C++
  5. Compilador GCC (compilador C)
  6. Java Swing para a interface
 
