package work;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WordInstance {
	public WordInstance() {
		
	}

	private ActiveXComponent objWord;
	private Dispatch document;
	private Dispatch wordObject;
	private Dispatch selection;
	
	public void open(String filename) {
		// Instantiate objWord
		objWord = new ActiveXComponent("Word.Application");

		// Assign a local word object
		wordObject = objWord.getObject();

		// Create a Dispatch Parameter to hide the document that is opened
		Dispatch.put(wordObject, "Visible", new Variant(true));

		// Instantiate the Documents Property
		Dispatch documents = objWord.getProperty("Documents").toDispatch();		
				
		// Open a word document, Current Active Document
		document = Dispatch.call(documents, "Open", filename).toDispatch();
		selection = objWord.getProperty("Selection").toDispatch();
		//objWord.setProperty("Visible", new Variant(true));
		//Dispatch tables = document.get(document,"Tables").toDispatch();
		
		
		
	}
	
	public void printToBookmark(String bookmark, String text){
		Dispatch.call(selection,"GoTo",new Variant(-1),null,null,bookmark);
		Dispatch.call(selection, "TypeText", text);
	}
	
	public void goToBookmark(String bookmark){
		Dispatch.call(selection,"GoTo",new Variant(-1),null,null,bookmark);		
	}
	
	public void printText(String text){
		Dispatch.call(selection, "TypeText", text);		
	}
	
	public void moveRight(){
		Dispatch.call(selection,"MoveRight",new Variant(12),new Variant(1),new Variant(0));		
	}
	
	public void moveDown(){
		Dispatch.call(selection,"MoveDown",new Variant(5),new Variant(1));		
	}
	
	public void AddRow(){
		Dispatch.call(selection,"InsertRows",new Variant(1));
	}
	
	public void AddRows(int n){
		Dispatch.call(selection,"InsertRowsBelow",new Variant(n));
	}
	
	public void saveAs(String filename){
		Dispatch.call(document,"SaveAs",filename,new Variant(6));
	}
	
	public void pressEnter(){
		Dispatch.call(selection,"TypeParagraph");		
	}
	
	public void boldFont(){
		Dispatch dfont = Dispatch.get(selection, "Font").toDispatch();		
		Dispatch.put(dfont,"Bold",new Variant(9999998));
	}
	
	public void close() {
		// Close object
		Dispatch.call(document, "Close");
	}
	
	
}
