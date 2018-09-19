package lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JTextPane;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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

public class Main extends Thread {
	public void run(boolean stem, boolean stopWords, String busca, JTextPane resultados) {
		
		resultados.setText("");
		/*
			EnglishAnalyzer possui stemming usando Porter
			Para eliminar StopWords passar CharArraySet.EMPTY_SET no construtor no Analyzer
		*/
		Analyzer analyzer;
		if(stem && stopWords) {
			analyzer = new EnglishAnalyzer(CharArraySet.EMPTY_SET);
		} else if(stem && !stopWords) {
			analyzer = new EnglishAnalyzer();
		} else if(stopWords) {
			analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
		} else {
			analyzer = new StandardAnalyzer();
		}
		
		Directory index = new RAMDirectory();
		
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		
		IndexWriter w;
		try {
			w = new IndexWriter(index, config);
			File[] fList = new File("./data").listFiles();
			for(File file : fList) {
				if(file.isDirectory()) {
					File[] files = new File(file.toString()).listFiles();
					for (int i = 0; i < files.length; i++) {
						File f = new File(files[i].toString());
						String title = f.getName();
						String body = String.join("\n", Files.readAllLines(Paths.get(f.getAbsolutePath())));
						addDoc(w, title, body);
					}
				}
			}
			w.close();
			
			//String queryString = "Danger morbid obesity for teenagers";
			//String queryString = "Neural networks applied on recommendation algorithms";
			String queryString = busca;
			Query q = new QueryParser("body", analyzer).parse(queryString);
			
			int hitsPerPage = 10;
	        IndexReader reader = DirectoryReader.open(index);
	        IndexSearcher searcher = new IndexSearcher(reader);
	        TopDocs docs = searcher.search(q, hitsPerPage);
	        ScoreDoc[] hits = docs.scoreDocs;

	        // 4. display results
	        resultados.setText(resultados.getText() + "Found " + hits.length + " hits.");
	        for(int i = 0; i < hits.length; ++i) {
	            int docId = hits[i].doc;
	            Document d = searcher.doc(docId);
				resultados.setText(resultados.getText() + "\n" + (i + 1) + ". " + "\t" + d.get("title") + '\n');
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
	
	private void addDoc(IndexWriter w, String title, String body) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("body", body, Field.Store.YES));
		w.addDocument(doc);
	}
}

	 
