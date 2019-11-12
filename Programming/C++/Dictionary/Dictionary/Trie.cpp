#include "pch.h"
#include "Trie.h"


Trie::Trie()
{
	root = new Node;
	root->symbol = '#';
	root->v.resize(26, nullptr);
	root->word = "#";
}

void Trie::add_word(std::string w, std::string t)
{
	Node* pos = root;
	for (int i = 0; i < w.size(); i++)
	{
		if (pos->v[w[i] - 'a'] == nullptr)
		{
			Node* q = new Node;
			q->symbol = w[i];
			q->v.resize(26, nullptr);
			q->word = "#";
			pos->v[w[i] - 'a'] = q;
		}
		pos = pos->v[w[i] - 'a'];
	}
	pos->word = t;
}

std::string Trie::translate(std::string w)
{
	Node* pos = root;
	for (int i = 0; i < w.size(); i++)
	{
		if (pos->v[w[i] - 'a'] == nullptr)
		{
			return "Error! Dictionary doesn't contain this word";
		}
		pos = pos->v[w[i] - 'a'];
	}
	if (pos->word == "#")
	{
		return "Error! Dictionary doesn't contain this word";
	}
	return pos->word;
}

Trie::~Trie()
{
	delete root;
}