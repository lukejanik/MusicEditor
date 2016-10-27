package cs3500.music.view;

import cs3500.music.model.IMusicEditorModel;

/**
 * Created by lukecjanik on 10/26/16.
 */
public class ConsoleView implements IView {

  @Override
  public void render(IMusicEditorModel model) {
    System.out.print(model.getEditorState());
  }
}
