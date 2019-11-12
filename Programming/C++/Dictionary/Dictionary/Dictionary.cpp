#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>
//#include "Trie.h"
#include "trieOnList.h"
//#include <Windows.h>
int main()
{
	setlocale(LC_CTYPE, "");
	std::ifstream vin("vocabulary.txt");
	std::ifstream fin("input.txt");
	std::ofstream fout("output.txt");
	//Trie dict;
	trieOnList dict;
	while (!(vin.eof()))
	{
		std::string w, t;
		vin >> w >> t;
		dict.add_word(w, t);
	}
	//while (!(fin.eof()))
	std::string w;
	//fin >> w;
	std::getline(std::cin, w);
	while (w[0] != '\0')
	{
		fout << w << " " << dict.translate(w) << '\n';
		std::cout << w << " " << dict.translate(w) << '\n';
		//fin >> w;
		std::getline(std::cin, w);
	}
	return 0;
}