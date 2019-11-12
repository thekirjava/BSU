#include "pch.h"
#include <iostream>

using namespace std;

int main()
{

	unsigned short int x; //число которое проверяется на простоту
	cin >> x;
	unsigned short int i = 2;
	unsigned int ans = 0;
	_asm
	{
		mov ax, x
		cwd // х записывается в dx : ax
		div i // деление на два
		mov bx, ax// запись половины в другой регистр
	in1:
		mov ax, x
		cwd// х записывается в dx : ax
		cmp bx, i
		jb out1// цикл пока x/2 не меншье i
		div i
		cmp dx, 0 //проверка на делимость
		je not_simple
		add i, 1 //увеличение числа для проверки
		jmp in1
	out1:
		mov ans, 0 //сюда программа попадает если прошел весь цикл и число простое
		jmp end1
	not_simple: //сюда программа переходит, если число на что-то разделилось и оно непростое
		mov ans, 1
	end1:
	}
	if (ans == 0)
	{
		cout << "Simple";
	}
	else
	{
		cout << "Not simple";
	}
	return 0;
}
