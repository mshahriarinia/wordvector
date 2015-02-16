

# To Run

```
mvn exec:java -Dexec.mainClass="edu.ufl.cise.wordvector.App"
```

# Preprocessing
Split freebase to enable parallel processing
```
split ../freebase-rdf-latest -l 60000000 -d
```

Extract type.object.name 
```
cat freebase-rdf-latest | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/type.object.name>\s"\([^"]*\)"@en.*;\1 \t \2;p' | head > freebase-rdf-latest.subj.name
sed '/^.\{100\}..*/d' freebase-rdf-latest.subj.name > freebase-rdf-latest.subj.name.shortlines   # extract only short lines
sed '/[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/d'  freebase-rdf-latest.subj.name.shortlines > freebase-rdf-latest.subj.name.shortlines.nonum   # remove lines that contain more than 10 numbers in them (ISBN?)
```

TODO: Extract ```<http://rdf.freebase.com/ns/astronomy.astronomical_observatory.discoveries>     <http://www.w3.org/2000/01/rdf-schema#label>    "Discoveries"@en```

It can be used for ```<http://rdf.freebase.com/ns/m.03gzbb>   <http://rdf.freebase.com/ns/astronomy.astronomical_observatory.discoveries>     <http://rdf.freebase.com/ns/m.010f4hvb> .```
```
cat freebase-rdf-latest | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://www.w3.org/2000/01/rdf-schema#label>\s"\([^"]*\)"@en.*;\1 \t \2;p' > freebase-rdf-latest.subj.label

```

=============


Determine *subject* Unique name spaces
```
cat freebase-rdf-latest | sed -n 's;<\([^>]*\)/[^>]*>.*;\1;p' > freebase-rdf-latest.subj.namespae
```

Determine *predicate* Unique name spaces
```
cat freebase-rdf-latest | sed -n 's;<[^>]*>\s*<\([^>]*\)/\([^>]*\)>.*;\1 \t \2;p' | sort | uniq  
```

```
cat *sorted > type.object.name.rdf.schema.label.all-sorted
```


==================

## Yan's snippets
remove name spaces
```
zcat freebase-rdf-latest.gz | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^]]*\)>.*;\1 \2 \3;p' | head
zcat freebase-rdf-latest.gz | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^>]*\)>\s*"\([^"]*\)".*;\1 \2 \3;p' | head
```

