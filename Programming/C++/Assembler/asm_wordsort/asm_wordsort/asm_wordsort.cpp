#include "pch.h"
#include <iostream>
#include <cstring>
using namespace std;

int main()
{
	const int N = 110;
	const int x = 4;
	int newsize;
	char text[N] = { "One               two  three        four five six   seven  eight         eight    nine             ten   four" };
	char ans[N];
	char substring[N];
	int leng[N];
	int pointer[N];
	int word_counter;
	int space_counter;
	int start;
	int mid;
	int sum;
	int newnewsize;
	int point;
	_asm
	{
		mov ecx, N
		mov word_counter, 1
		mov space_counter, 0
		mov al, ' '
		lea edi, text
		dec edi
		space_delete:
			inc edi
			repne scasb
			jecxz eol
			mov start, edi
			add space_counter, ecx
			repe scasb
			sub space_counter, ecx
			dec space_counter
			inc word_counter
			dec edi
			cmp start, edi
			je space_delete
			mov esi, edi
			push ecx
			push edi
			mov edi, start
			rep movsb
			pop edi
			pop ecx
			mov edi, start
			cmp ecx, 0
			jne space_delete
		eol:
		mov ecx, N
		sub ecx, space_counter
		mov newsize, ecx
		lea edi, text
		xor esi, esi
		xor eax, eax
		mul x
		mov al, ' '
		word_length:
			cmp esi, word_counter
			jnl counted
			mov leng[esi * 4], ecx
			mov pointer[esi*4], edi
			repne scasb
			sub leng[esi * 4], ecx
			dec leng[esi * 4]
			inc esi
			jmp word_length
		counted:
		/*xor esi, esi
		xor eax, eax
		middle:
			cmp esi, word_counter
			jnl sum
			add eax, leng[esi*4]
			inc esi
			jmp middle
		sum:
		cdq
		div word_counter
		mov mid, eax*/
		xor esi, esi
		xor edi, edi
		bubble_1:
			cmp esi, word_counter
			jnl sorted
			mov edi, esi
			inc edi
			bubble_2:
				cmp edi, word_counter
				jnl end_bubble_2
				mov eax, leng[esi * 4]
				mov edx, leng[edi * 4]
				cmp eax, edx
				jl not_swap
				mov leng[esi * 4], edx
				mov leng[edi * 4], eax
				mov eax, pointer[esi * 4]
				mov edx, pointer[edi * 4]
				mov pointer[esi * 4], edx
				mov pointer[edi * 4], eax
				not_swap:
				inc edi
				jmp bubble_2
			end_bubble_2:
			inc esi
			jmp bubble_1
		sorted:
		
		mov edx, 0
		mov newnewsize, 0
		mov sum, 0
		lea esi, text
		mov ebx, word_counter
		
		build_string:	
		cmp edx, ebx
			je finish
			lea esi, text
			mov ecx, leng[edx * 4]
			mov esi, pointer[edx*4]
			lea edi, substring
			rep movsb
			mov al, substring
			lea edi, ans
			mov ecx, newsize
			cld
			search:
				repne scasb
				jecxz not_found
				push edi
				push ecx
				mov ecx, leng[edx*4]
				dec ecx
				lea esi, substring + 1
				repe cmpsb
				jz is_found
				pop ecx
				pop edi
				jmp search
				is_found:
					pop ecx
					pop edi
					dec word_counter
					inc edx
					jmp build_string
				not_found:
					lea edi, ans
					lea esi, text
					cmp newnewsize, 0
					je first_word
					mov edi, point
					first_word:
					mov esi, pointer[edx * 4]
					mov ecx, leng[edx * 4]
					add newnewsize, ecx
					inc newnewsize
					add sum, ecx
					rep movsb
					mov [edi], ' ' 
					inc edi
					mov point, edi
					inc edx
					jmp build_string	
		finish:
			dec newnewsize
			mov eax, sum
			cdq
			div word_counter
			mov mid, eax
	}
	newsize--;
	cout << mid << '\n';
	for (int i = 0; i < newsize; i++)
	{
		cout << text[i];
	}
	cout << '\n';
	for (int i = 0; i < newnewsize; i++)
	{
		cout << ans[i];
	}
	return 0;
}
