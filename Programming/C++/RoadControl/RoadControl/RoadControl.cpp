#include "pch.h"
#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

int main()
{
	ifstream in("input.txt");
	ofstream out("output.txt");
	int N;
	in >> N;
	vector < vector<int> > ssm(N);
	int roadcnt = 0;
	for (int i = 0; i < N; i++)
	{
		int M;
		in >> M;
		roadcnt += M;
		ssm[i].resize(M);
		for (int j = 0; j < M; j++)
		{
			in >> ssm[i][j];
			ssm[i][j]--;
		}
		
	}
	roadcnt /= 2;
	int ans = 0;
	while (roadcnt != 0)
	{
		int pos = 0;
		for (int i = 0; i < N; i++)
		{
			if (ssm[i].size() > ssm[pos].size())
			{
				pos = i;
			}
		}
		roadcnt -= ssm[pos].size();
		for (int i = 0; i < ssm[pos].size(); i++)
		{
			ssm[ssm[pos][i]].erase(find(ssm[ssm[pos][i]].begin(), ssm[ssm[pos][i]].end(), pos));
		}
		ssm[pos].clear();
		ans++;
	}
	out << ans;
	return 0;
}
