#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	int a, b;
	int i = 2;
	int x = 0;
	int ans = 0;
	cin >> a >> b;
	_asm
	{
	in1:
		mov eax, a
		cmp eax, b
		je out1
			mov x, eax
			cdq // х записывается в edx : eax
			div i // деление на два
			mov ebx, eax// запись половины в другой регистр
		in2:
			mov eax, x
			cdq// х записывается в edx : eax
			cmp ebx, i
			jb out2// цикл пока x/2 не меншье i
				div i
				cmp edx, 0 //проверка на делимость
				je not_simple
				inc i //увеличение числа для проверки
			jmp in2
		out2:
			add ans, 1 //сюда программа попадает если прошел весь цикл и число простое
			jmp end1
		not_simple : //сюда программа переходит, если число на что-то разделилось и оно непростое
			add ans, 0
		end1 :
			inc a
			mov i, 2
		jmp in1
	out1:
	}
	cout << ans;
	return 0;
}
