package lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import javafx.scene.shape.Path;

public class Main extends Thread {
	public void run(boolean stem, boolean stopWords, String busca) {
		System.out.println(stem);
		System.out.println(stopWords);
		System.out.println(busca);
		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory index = new RAMDirectory();
		
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		
		IndexWriter w;
		try {
			w = new IndexWriter(index, config);
			File[] numOfFiles = new File("./data/obesity").listFiles();
			for (int i = 0; i < 30; i++) {
				File f = new File(numOfFiles[i].toString());
				String title = f.getName();
				String body = String.join("\n", Files.readAllLines(Paths.get(f.getAbsolutePath())));
				addDoc(w, title, body);
			}
			w.close();
			
			String queryString = busca;
			Query q = new QueryParser("title", analyzer).parse(queryString);
			
			int hitsPerPage = 10;
	        IndexReader reader = DirectoryReader.open(index);
	        IndexSearcher searcher = new IndexSearcher(reader);
	        TopDocs docs = searcher.search(q, hitsPerPage);
	        ScoreDoc[] hits = docs.scoreDocs;

	        // 4. display results
	        System.out.println("Found " + hits.length + " hits.");
	        for(int i=0;i<hits.length;++i) {
	            int docId = hits[i].doc;
	            Document d = searcher.doc(docId);
	            System.out.println((i + 1) + ". " + "\t" + d.get("title"));
	        }

	        // reader can only be closed when there
	        // is no need to access the documents any more.
	        reader.close();
	 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void addDoc(IndexWriter w, String title, String body) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("body", body, Field.Store.YES));
		w.addDocument(doc);
	}
}

	 
