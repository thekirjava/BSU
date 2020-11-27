import reader
import tagger

r = reader.Reader('korpus.xml')
maps = r.parse()
t = tagger.Tagger(maps[0], 'input.txt')
ans = t.tag()
print(ans)