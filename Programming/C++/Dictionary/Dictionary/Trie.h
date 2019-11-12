#pragma once
#include <vector>
#include <string>

class Trie
{
public:
	Trie();
	void add_word(std::string w, std::string t);
	std::string translate(std::string w);
	~Trie();
private:
	struct Node
	{
		char symbol;
		std::vector <Node*> v;
		std::string word;
	};
	Node *root;
};