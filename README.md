# wordvector


cat sample.txt | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/type.object.name>\s*\(\.\)*;\1 \2;p' | head







# Yan's snippets

zcat freebase-rdf-latest.gz | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^]]*\)>.*;\1 \2 \3;p' | head

zcat freebase-rdf-latest.gz | sed -n 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^>]*\)>\s*"\([^"]*\)".*;\1 \2 \3;p' | head
