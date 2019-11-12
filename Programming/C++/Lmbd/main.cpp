#include <iostream>
#include <list>
#include <algorithm>
#include <cmath>
#include <string>
#include <windows.h>
#include <sstream>

using namespace std;

template<typename T>
double FSums(T x) {
    return x;
}

template<typename T, typename ... Args>
double FSums(T x, Args ... arg) {
    auto f = [](Args ... arg) { return FSums(arg...); };
    return x + f(arg...);
}

template<typename T>
string ToString(char split, T c) {
    stringstream s;
    s << c;
    return s.str();
}

template<typename T, typename ... Args>
string ToString(char split, T c, Args ... arg) {
    auto f = [](char s, Args ... arg) { return ToString(s, arg...); };
    stringstream s;
    s << c << split << f(split, arg...);
    return s.str();
}

int main() {
    SetConsoleOutputCP(1251);
    //Задача 1
    list<int> l;
    bool flag = true;
    cout << "Список команд:\n";
    cout << "Добавить элемент X в конец: 1 X\n";
    cout << "Добавить элемент X в начало: 2 X\n";
    cout << "Удалить первый элемент: 3\n";
    cout << "Удалить последний элемент: 4\n";
    cout << "Вывести список: 5\n";
    cout << "Отсортировать список: 6\n";
    cout << "Вывести первое четное число: 7\n";
    cout << "Посчитать количество четных: 8\n";
    cout << "Заменить отрицательные на ноль: 9\n";
    cout << "Перейти к задаче 2: 0\n";
    while (flag) {

        int cmd;
        cin >> cmd;
        switch (cmd) {
            case 1: {
                int x;
                cin >> x;
                l.push_back(x);
            }
                break;
            case 2: {
                int x;
                cin >> x;
                l.push_front(x);
            }
                break;
            case 3: {
                l.pop_front();
            }
                break;
            case 4: {
                l.pop_back();
            }
                break;
            case 5: {
                for_each(l.begin(), l.end(), [](int x) { cout << x << " "; });
                cout << '\n';
            }
                break;
            case 6: {
                l.sort([](int x, int y) { return abs(x) < abs(y); });
            }
                break;
            case 7: {
                int ans = 1;
                for_each(l.begin(), l.end(), [&ans](int x) mutable {
                    if ((ans == 1) && (x % 2 == 0)) {
                        ans = x;
                    }
                });
                if (ans == 1) {
                    cout << "Чётных нет\n";
                } else {
                    cout << "Первое чётное -" << ans << '\n';
                }
            }
                break;
            case 8: {
                int cnt = 0;
                for_each(l.begin(), l.end(), [&cnt](int x) mutable {
                    if (x % 2 == 0) {
                        cnt++;
                    }
                });
                cout << "Всего чётных - " << cnt << '\n';
            }
                break;
            case 9: {
                for_each(l.begin(), l.end(), [](int &x) mutable {
                    if (x < 0) {
                        x = 0;
                    }
                });
            }
                break;
            case 0: {
                flag = false;
            }
                break;
            default: {
                cout << "Неверная команда!\n";
            }
                break;
        }
    }
    system("cls");
    //Задача 2
    short m = 13;
    int n1 = 17, n2 = 23;
    double x1 = 12.8;
    auto xSum = FSums(n1, 4.7, m, 1.5, 10, n2, 'a');
    cout << xSum << '\n';
    //Задача 3
    int n3 = 17;
    double x2 = 6.75;
    string ans = ToString(';', 25, 3.7, n3, x2);
    cout << ans <<'\n';
}
