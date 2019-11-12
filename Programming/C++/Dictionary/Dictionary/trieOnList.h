#pragma once
#include <vector>
#include <string>

class trieOnList
{
public:
	trieOnList();
	void add_word(std::string w, std::string t);
	std::string translate(std::string w);
	~trieOnList();
private:
	struct Node
	{
		char symbol;
		Node* nextSymbol;
		Node* nextNode;
		std::string word;
	};
	Node *root;
};