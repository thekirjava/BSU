#include "pch.h"
#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <vector>
#include <algorithm>
#include <queue>

using namespace std;

map <char, string> symbol_code;

struct Node
{
	char symbol;
	int k;
	Node* left;
	Node* right;
};

bool cmp(Node* x, Node* y)
{
	return x->k < y->k;
}

bool cmp1(Node* x, Node* y)
{
	return x->k > y->k;
}
void dfs(Node* pos, string code)
{
	if ((pos->left == nullptr) && (pos->right == nullptr))
	{
		symbol_code[pos->symbol] = code;
	}
	if (pos->left != nullptr)
	{
		dfs(pos->left, code + "1");
	}
	if (pos->right != nullptr)
	{
		dfs(pos->right, code + "0");
	}
}

int main()
{
	char type;
	cout << "Input 'c' to compress file, input 'd' to decompress ";
	cin >> type;
	while ((type != 'c') && (type != 'd'))
	{
		cout << "Input 'c' to compress file, input 'd' to decompress ";
		cin >> type;
	}
	string filename;
	cout << "Input file name ";
	cin >> filename;
	if (type == 'c')
	{
		ifstream in(filename + ".txt");
		//ofstream out(filename + "_compressed.txt");
		ofstream out(filename + "_cmpkey.txt");
		ofstream binout(filename + "_cmp.txt"/*, ios::binary|ios::out*/);
		map <char, int> symbol_cnt;
		vector <string> text;
		while (!(in.eof()))
		{
			string s;
			getline(in, s);
			s.push_back('\n');
			text.push_back(s);
			for (int i = 0; i < s.size(); i++)
			{
				symbol_cnt[s[i]]++;
			}
		}
		text[text.size() - 1].pop_back();
		vector <Node*> tree;
		for (map <char, int>::iterator iter = symbol_cnt.begin(); iter != symbol_cnt.end(); iter++)
		{
			Node* q = new Node;
			q->symbol = iter->first;
			q->k = iter->second;
			q->left = nullptr;
			q->right = nullptr;
			tree.push_back(q);
		}
		int sz = tree.size();
		sz += sz - 1;
		int num = 0;
		while (tree.size() != sz)
		{
			sort(tree.begin(), tree.end(), cmp);
			Node* q = new Node;
			q->left = tree[num];
			q->right = tree[num + 1];
			q->k = tree[num]->k + tree[num + 1]->k;
			q->symbol = '#';
			tree.push_back(q);
			num += 2;
		}
		dfs(tree[tree.size() - 1], "");
		int x = symbol_code.size();
		out << x << '\n';
		for (map <char, string>::iterator iter = symbol_code.begin(); iter != symbol_code.end(); iter++)
		{
			out << iter->first << " " << iter->second << '\n';
		}
		for (int i=0; i<text.size(); i++)
		{ 
			for (int j = 0; j < text[i].size(); j++)
			{
				for (int k = 0; k < symbol_code[text[i][j]].size(); k++)
				{
					binout << symbol_code[text[i][j]];
					//binout.write((char*)&symbol_code[text[i][j]][k], sizeof symbol_code[text[i][j]][k]);
					//cout << (char*)&symbol_code[text[i][j]][k] << " " << sizeof symbol_code[text[i][j]][k];
				}
			}
		}
	}
	else
	{
		//ifstream in(filename + ".txt");
		ifstream in(filename + "key.txt");
		ifstream binin(filename + ".txt"/*,  ios::binary|ios::in*/);
		ofstream out(filename + "_decompressed.txt");	
		int N;
		in >> N;
		vector <Node*> dict;
		dict.push_back(new Node);
		dict[0]->symbol = '#';
		dict[0]->k = -1;
		dict[0]->left = nullptr;
		dict[0]->right = nullptr;
		string buf;
		getline(in, buf);
		for (int i = 0; i < N; i++)
		{
			int c;
			string code;
			getline(in, code);
			if (code.size() <= 1)
			{
				getline(in, code);
				c = '\n';
				code.erase(code.begin());
			}
			else
			{
				c = code[0];
				code.erase(code.begin(), code.begin() + 2);
			}
			Node* pos = dict[0];
			for (int j = 0; j < code.size(); j++)
			{
				if (code[j] == '1')
				{
					if (pos->left == nullptr)
					{
						Node* q = new Node;
						q->symbol = '#';
						q->symbol = -1;
						q->left = nullptr;
						q->right = nullptr;
						pos->left = q;
						dict.push_back(q);
					}
					pos = pos->left;
				}
				else
				{
					if (pos->right == nullptr)
					{
						Node* q = new Node;
						q->symbol = '#';
						q->k = -1;
						q->left = nullptr;
						q->right = nullptr;
						pos->right = q;
						dict.push_back(q);
					}
					pos = pos->right;
				}
			}
			pos->symbol = c;
		}
		Node* pos = dict[0];
		while (!(binin.eof()))
		{
			char c;
			binin >> c;
			//binin.read((char*)&c, sizeof c);
			if (c == '1')
			{
				pos = pos->left;
			}
			else
			{
				pos = pos->right;
			}
			if ((pos->left == nullptr) && (pos->right == nullptr))
			{
				out << pos->symbol;
				pos = dict[0];
			}
		}
	}
}

