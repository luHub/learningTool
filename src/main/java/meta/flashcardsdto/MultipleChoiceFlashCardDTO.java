package meta.flashcardsdto;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceFlashCardDTO {

	//TODO create question number and id
	private int id;
	private Question question;
	//TODO Cheange this From list to SET
	private List<Choice> choiceList = new ArrayList<Choice>();
	private Answer answer = new Answer();
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<Choice> getChoiceList() {
		return choiceList;
	}
	public void setChoiceList(List<Choice> choiceList) {
		this.choiceList = choiceList;
	}
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((choiceList == null) ? 0 : choiceList.hashCode());
		result = prime * result + id;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MultipleChoiceFlashCardDTO))
			return false;
		MultipleChoiceFlashCardDTO other = (MultipleChoiceFlashCardDTO) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (choiceList == null) {
			if (other.choiceList != null)
				return false;
		} else if (!choiceList.equals(other.choiceList))
			return false;
		if (id != other.id)
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}
	
	
}
