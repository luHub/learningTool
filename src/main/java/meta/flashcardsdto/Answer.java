package meta.flashcardsdto;

public class Answer {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CorrectChoiceId;
		result = prime * result + ((answerText == null) ? 0 : answerText.hashCode());
		result = prime * result + (isAtrue ? 1231 : 1237);
		result = prime * result + (isBtrue ? 1231 : 1237);
		result = prime * result + (isCtrue ? 1231 : 1237);
		result = prime * result + (isDtrue ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Answer))
			return false;
		Answer other = (Answer) obj;
		if (CorrectChoiceId != other.CorrectChoiceId)
			return false;
		if (answerText == null) {
			if (other.answerText != null)
				return false;
		} else if (!answerText.equals(other.answerText))
			return false;
		if (isAtrue != other.isAtrue)
			return false;
		if (isBtrue != other.isBtrue)
			return false;
		if (isCtrue != other.isCtrue)
			return false;
		if (isDtrue != other.isDtrue)
			return false;
		return true;
	}

	private int CorrectChoiceId;
	private String answerText;
	private boolean isAtrue=false;
	private boolean isBtrue=false;
	private boolean isCtrue=false;
	private boolean isDtrue=false;

	public Answer(){}
	
	public Answer(int id, String answer,boolean a,boolean b,boolean c,boolean d){
		this.CorrectChoiceId=id;
		this.answerText=answer;
		this.isAtrue=a;
		this.isBtrue=b;
		this.isCtrue=c;
		this.isDtrue=d;
	}
	
	public int getAnswerId() {
		return CorrectChoiceId;
	}
	public void setAnswerId(int answerId) {
		this.CorrectChoiceId = answerId;
	}
	public String getAnswer() {
		return answerText;
	} 
	public void setAnswer(String answerText) {
		this.answerText = answerText;
	}

	public boolean isAtrue() {
		return isAtrue;
	}

	public void setAtrue(boolean isAtrue) {
		this.isAtrue = isAtrue;
	}

	public boolean isBtrue() {
		return isBtrue;
	}

	public void setBtrue(boolean isBtrue) {
		this.isBtrue = isBtrue;
	}

	public boolean isCtrue() {
		return isCtrue;
	}

	public void setCtrue(boolean isCtrue) {
		this.isCtrue = isCtrue;
	}

	public boolean isDtrue() {
		return isDtrue;
	}

	public void setDtrue(boolean isDtrue) {
		this.isDtrue = isDtrue;
	}
}
