import reader
import tagger
import pos_tag

r = reader.Reader('korpus.xml')
words, prefix, postfix, tags = r.parse()
t = tagger.Tagger(words, 'input.txt')
ds = t.tag()
pt = pos_tag.POSTagger(words, prefix, postfix, tags)
pt.train(ds)

