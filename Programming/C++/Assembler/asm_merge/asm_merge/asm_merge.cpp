#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	int N, M;
	cin >> N >> M;
	int a[10000];
	int b[10000];
	for (int i = 0; i < N; i++)
	{
		cin >> a[i];
	}
	for (int i = N; i < 10000; i++)
	{
		a[i] = 0;
	}
	for (int i = 0; i < M; i++)
	{
		cin >> b[i];
	}
	for (int i = M; i < 10000; i++)
	{
		b[i] = 0;
	}
	int ans[20000];
	for (int i = 0; i < 20000; i++)
	{
		ans[i] = 0;
	}
	int i = 0;
	int anssize = 0;
	_asm
	{
		lea eax, a
		lea ebx, b
		xor esi, esi
		xor edi, edi
		xor ecx, ecx
		mov edx, [eax][esi*4]
		mov ans[ecx*4], edx
		mov edx, [ebx][edi*4]
		cmp ans[ecx], edx
		JG moveb
			inc esi
			jmp cycle1
		moveb :
			mov edx, [ebx][edi*4]
			mov ans[ecx*4], edx
			inc edi
		cycle1 :
		cmp esi, N
			JNL bcheck
			cmp edi, M
			JNL acheck
			mov edx, [eax][esi * 4]
			cmp edx, [ebx][edi * 4]
			JG bmove
			cmp ans[ecx * 4], edx
			JE notmove1
			inc ecx
			mov ans[ecx * 4], edx
			notmove1 :
		inc esi
			jmp cycle1
			bmove :
		mov edx, [ebx][edi * 4]
			cmp ans[ecx * 4], edx
			JE notmove2
			inc ecx
			mov ans[ecx * 4], edx
			notmove2 :
		inc edi
			jmp cycle1
			acheck :
		cmp esi, N
			JNL end1
				mov edx, [eax][esi * 4]
				cmp ans[ecx * 4], edx
				JE notmove3
					inc ecx
					mov ans[ecx * 4], edx
				notmove3:
				inc esi
		jmp acheck
		bcheck:
			cmp edi, M
			JNL end1
				mov edx, [ebx][edi*4]
				cmp ans[ecx * 4], edx
				JE notmove4
					inc ecx
					mov ans[ecx * 4], edx
				notmove4:
				inc edi
		jmp bcheck
	end1:
		inc ecx
			mov anssize, ecx
	}
	for (int i = 0; i < anssize; i++)
	{
		cout << ans[i] << " ";
	}
	return 0;
}