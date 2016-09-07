package commons.flashcards;

import java.util.ArrayList;
import java.util.List;

import meta.flashcardsdto.Answer;
import meta.flashcardsdto.Choice;
import meta.flashcardsdto.MultipleChoiceFlashCardDTO;
import meta.flashcardsdto.Question;

public class FlashCardCreator {
	
	private List<Choice> choiceList = new ArrayList<Choice>();
	private Question question;
	private Answer answer;
	private int id=0;
	
	public void prepareQuestion(int id,String type, String questionText){
		this.question = createQuestion(id,type, questionText);
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void addToChoiceList(int id, String choiceDescription){
		Choice choise = createChoice(id, choiceDescription);
		this.choiceList.add(choise);
	}
	
	public void prepareAnswer(int id, String answer,boolean a,boolean b, boolean c, boolean d){
		this.answer=createAnswer(id, answer,a,b,c,d);
	}

	public MultipleChoiceFlashCardDTO create(){
		 return createMultipleChiceFlashCard(id,question, choiceList, answer);
	}
	
	
	private static Question createQuestion(final int id,final String type,final String questionText ){
		Question question = new Question(id,type,questionText);
		return question;
	}
	
	private static Choice createChoice(int id, String choiceDescription){
		Choice choice = new Choice(id,choiceDescription);
		return choice;
	}
	
	private static Answer createAnswer(int id, String answerDescription,boolean a,boolean b,boolean c,boolean d){
		Answer answer = new Answer(id,answerDescription,a,b,c,d);
		return answer;
	}
	
	private static MultipleChoiceFlashCardDTO createMultipleChiceFlashCard(int id,Question question, List<Choice> choiceList, Answer answer){
		
		MultipleChoiceFlashCardDTO multipleChoiceFlashCardDTO = new MultipleChoiceFlashCardDTO();
		multipleChoiceFlashCardDTO.setQuestion(question);
		multipleChoiceFlashCardDTO.setChoiceList(choiceList);
		multipleChoiceFlashCardDTO.setAnswer(answer);
		multipleChoiceFlashCardDTO.setId(id);
		return multipleChoiceFlashCardDTO;
	}

	
}
