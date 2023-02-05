package apptive.fruitable.board.exception;

public class NoTagsException extends RuntimeException{

    private String tagContent;
    private static final String MESSAGE = " (이)라는 태그는 존재하지 않습니다.";

    public NoTagsException(String tagContent) {
        super(tagContent + MESSAGE);
        this.tagContent = tagContent;
    }
}
