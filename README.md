# wordvector








Extract type.object.name 
```
cat freebase-rdf-latest | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/type.object.name>\s"\([^"]*\)"@en.*;\1 \t \2;p' | head > freebase-rdf-latest.subj.name
sed '/^.\{100\}..*/d' freebase-rdf-latest.subj.name > freebase-rdf-latest.subj.name.shortlines   # extract only short lines
```

Determine *subject* Unique name spaces
```
cat freebase-rdf-latest | sed -n 's;<\([^>]*\)/[^>]*>.*;\1;p' > freebase-rdf-latest.subj.namespae
```

Determine *predicate* Unique name spaces
```
cat freebase-rdf-latest | sed -n 's;<[^>]*>\s*<\([^>]*\)/\([^>]*\)>.*;\1 \t \2;p' | head -n 100000 | sort | uniq  
```


# Yan's snippets
remove name spaces
```
zcat freebase-rdf-latest.gz | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^]]*\)>.*;\1 \2 \3;p' | head
zcat freebase-rdf-latest.gz | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^>]*\)>\s*"\([^"]*\)".*;\1 \2 \3;p' | head
```
