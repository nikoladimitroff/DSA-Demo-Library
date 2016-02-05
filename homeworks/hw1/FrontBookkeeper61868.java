
public class FrontBookkeeper61868 implements IFrontBookkeeper {
	public Army army;
	
	public FrontBookkeeper61868(){
		this.army = new Army();
	}
    public String updateFront(String[] news) {
		return IO.doAllNews(news, this.army);
	}   
}
