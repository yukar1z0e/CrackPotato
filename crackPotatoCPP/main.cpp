#include <iostream>
#include <windows.h>

using namespace std;

void writeReslutToLog(){
    string command = R"(adb logcat crackPotato:D *:S *:W *:E *:F *:S -f /data/local/tmp/potatoLog.txt)";
    WinExec(command.c_str(),0);
}

void pullReslut() {
    string command = R"(adb pull /data/local/tmp/potatoLog.txt C:\Users\yukar0z0e\Desktop)";
    system(command.c_str());
}

void getResult() {
    FILE *fid = fopen("C:/Users/yukar0z0e/Desktop/potatoLog.txt", "r");
    char line[0000];
    memset(line, 0, 0000);
    while (!feof(fid)) {
        fgets(line, 0000, fid);
        cout << line << endl;
    }
}

void search(string phoneNumber) {
    string command;
    command="adb shell input tap 000 000";
    system(command.c_str());
    command="adb shell input tap 000 000";
    system(command.c_str());
    command="adb shell input tap 000 000";
    system(command.c_str());
    command = "adb shell input text " + phoneNumber;
    system(command.c_str());
    command="adb shell input tap 000 0000";
    system(command.c_str());
    cout << "查询中" << endl;
    command="adb shell input tap 000 000";
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