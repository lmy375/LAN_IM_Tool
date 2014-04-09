package lit.object.messages;

public class AnswerMessage extends Message{
	/**
	 * Answer of request. True or false.
	 */
	boolean answer;
	int answerCode;
	public static final int ANS_REGISTER= 1;
	public static final int ANS_ADD_FRIEND=2;
	public static final int ANS_REMOVE_FRIEND = 3;
	public static final int ANS_LOGIN= 4;
	
	public static final int ANS_ONLINE=5;
	public static final int ANS_MODIFY_INFO = 6;
	//public static final int ANS_ONLINE = 5;
	//public static final int ANS_OFFLINE = 6;
	
	/**
	 * Detail of answer. Usually null when answer is true.
	 */
	String content;	
	
	public AnswerMessage(boolean answer ,int answerCode,  String content){
		super(Message.MSG_ANSWER, Message.ID_SERVER , "");
		this.answerCode = answerCode;
		this.answer = answer;
		this.content = content;
	}
	public boolean getAnswer(){
		return this.answer;
	}
	public void setAnswer(boolean answer){
		this.answer = answer;
	}
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public int getAnswerCode(){
		return this.answerCode;
	}
}
