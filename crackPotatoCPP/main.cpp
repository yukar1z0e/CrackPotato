#include <iostream>
#include <windows.h>

using namespace std;

void writeReslutToLog(){
    string command = R"(adb logcat crackPotato:D *:S *:W *:E *:F *:S -f /data/local/tmp/potatoLog.txt)";
    WinExec(command.c_str(),1);
}

void pullReslut() {
    string command = R"(adb pull /data/local/tmp/potatoLog.txt C:\Users\yukar1z0e\Desktop)";
    system(command.c_str());
}

void getResult() {
    FILE *fid = fopen("C:/Users/yukar1z0e/Desktop/potatoLog.txt", "r");
    char line[2048];
    memset(line, 0, 2048);
    while (!feof(fid)) {
        fgets(line, 2048, fid);
        cout << line << endl;
    }
}

void search(string phoneNumber) {
    string command;
    command="adb shell input tap 710 105";
    system(command.c_str());
    command="adb shell input tap 710 240";
    system(command.c_str());
    command="adb shell input tap 300 105";
    system(command.c_str());
    command = "adb shell input text " + phoneNumber;
    system(command.c_str());
    command="adb shell input tap 710 1100";
    system(command.c_str());
    cout << "查询中" << endl;
    command="adb shell input tap 710 105";
    system(command.c_str());
    cout << "查询完成" << endl;
    cout<<"继续查询"<<endl;
}

int main() {
    while (true)
    {
        string phoneNumber;
        cout << "输入手机号" << endl;
        cin >> phoneNumber;
        writeReslutToLog();
        if(phoneNumber=="0"){
            break;
        }
        search(phoneNumber);
        pullReslut();
        getResult();
    }
    cout<<"查询结束"<<endl;
    return 0;
}