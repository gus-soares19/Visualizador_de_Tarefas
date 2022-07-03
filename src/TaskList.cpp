#include <windows.h>
#include <stdio.h>
#include <tchar.h>
#include <psapi.h>
#include <TaskList.h>
#include <string>

using namespace std;

string getTask(DWORD processID)
{
    TCHAR szProcessName[MAX_PATH] = TEXT("<unknown>");
    HANDLE hProcess = OpenProcess(PROCESS_QUERY_INFORMATION |
                                      PROCESS_VM_READ,
                                  FALSE, processID);

    if (NULL != hProcess)
    {
        HMODULE hMod;
        DWORD cbNeeded;

        if (EnumProcessModules(hProcess, &hMod, sizeof(hMod),
                               &cbNeeded))
        {
            GetModuleBaseName(hProcess, hMod, szProcessName,
                              sizeof(szProcessName) / sizeof(TCHAR));
        }
    }

    int process_len = strlen(szProcessName);

    CloseHandle(hProcess);

    string processName = szProcessName;
    return processName + " - " + to_string(processID);
}

string getTasks(void)
{

    DWORD aProcesses[1024], cbNeeded, cProcesses;
    unsigned int i;

    if (!EnumProcesses(aProcesses, sizeof(aProcesses), &cbNeeded))
    {
        return "null";
    }

    cProcesses = cbNeeded / sizeof(DWORD);
    string processess;

    for (i = 0; i < cProcesses; i++)
    {
        if (aProcesses[i] != 0)
        {   
            processess = processess + getTask(aProcesses[i]) + "\n";
        }
    }

    return processess;
}

string getTaskInfos(DWORD processID)
{
    HANDLE hProcess;
    PROCESS_MEMORY_COUNTERS pmc;

    hProcess = OpenProcess(PROCESS_QUERY_INFORMATION |
                               PROCESS_VM_READ,
                           FALSE, processID);
    if (NULL == hProcess)
        return "null";

    if (GetProcessMemoryInfo(hProcess, &pmc, sizeof(pmc)))
    {

        string result = "PageFaultCount: ";
        result = result + to_string(pmc.PageFaultCount);

        result = result + "\nPeakWorkingSetSize: ";
        result = result + to_string(pmc.PeakWorkingSetSize);

        result = result + "\nWorkingSetSize: ";
        result = result + to_string(pmc.WorkingSetSize);

        result = result + "\nQuotaPeakPagedPoolUsage: ";
        result = result + to_string(pmc.QuotaPeakPagedPoolUsage);

        result = result + "\nQuotaPagedPoolUsage: ";
        result = result + to_string(pmc.QuotaPagedPoolUsage);

        result = result + "\nQuotaPeakNonPagedPoolUsage: ";
        result = result + to_string(pmc.QuotaPeakNonPagedPoolUsage);

        result = result + "\nQuotaNonPagedPoolUsage: ";
        result = result + to_string(pmc.QuotaNonPagedPoolUsage);

        result = result + "\nPagefileUsage: ";
        result = result + to_string(pmc.PagefileUsage);

        result = result + "\nPeakPagefileUsage: ";
        result = result + to_string(pmc.PeakPagefileUsage);

        return result;
    }

    CloseHandle(hProcess);
    return "null";
}

JNIEXPORT jstring JNICALL Java_TaskList_nativeGetTasks(JNIEnv *env, jobject obj)
{
        return env->NewStringUTF(getTasks().c_str());
}

JNIEXPORT jstring JNICALL Java_TaskList_nativeGetTaskInfos(JNIEnv *env, jobject obj)
{
    jclass cls = env->GetObjectClass(obj);
    jfieldID iField = env->GetFieldID(cls, "idProcesso", "J");
    jlong idProcesso = env->GetLongField(obj, iField);

    return env->NewStringUTF(getTaskInfos(idProcesso).c_str());
}