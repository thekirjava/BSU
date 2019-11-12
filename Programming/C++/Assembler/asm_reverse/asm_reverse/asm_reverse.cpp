#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	char ans[4] = { '0', '0', '0', '0' };
	int sz = 4;
	_asm
	{
		xor edi, edi
		mov eax, 0x1B3D
		mov ebx, 0x000F
		mov ecx, sz
		loop1:
			xor edx, edx
			mov edx, eax
			and edx, ebx
			cmp edx, 9
			JG symbol
				add edx, 0x0030
				jmp next1
			symbol:
				sub edx, 0x000A
				add edx, 0x0041
		next1:
			mov ans[edi], dl
			inc edi
			add esi, 4
			shr eax, 4
		loop loop1
	}
	for (int i = 0; i < 4; i++)
	{
		cout << ans[i];
	}
	return 0;
}