package com.evoxon.mastermind.domain;

import com.evoxon.mastermind.util.KeyboardInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MindServiceTest {

    @InjectMocks
    private MindService mindService;
    @Mock
    private KeyboardInput keyboardInput;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void shouldNotInitializeGame() {
        //given
        when(keyboardInput.getNewInput()).thenReturn("N");
        //when
        mindService.initializeGame();
        //then
        assertThat(outputStreamCaptor.toString()).contains("Goodbye");
    }



    @Test
    void shouldInitializeGame() {
        //given
        String[] secret = new String[]{"yellow", "red", "white", "black", "red"};
        MindGame mindGame = new MindGame(secret,0,false);
        MindService mindService1 = Mockito.spy(mindService);
        Mockito.doReturn(mindGame).when(mindService1).createNewGame();
        Mockito.doNothing().when(mindService1).playMindGame(mindGame);
        when(keyboardInput.getNewInput()).thenReturn("Y");
        //when
        mindService1.initializeGame();
        //then
        verify(mindService1,times(1)).playMindGame(mindGame);
        verify(mindService1,times(1)).createNewGame();
        verify(mindService1,times(1)).displayRules();
    }

    @Test
    void shouldCreateNewGame() {
        //given
        MindGame mindGame;
        //when
        mindGame = mindService.createNewGame();
        //then
        assertThat(mindGame.getSecretCombination().length).isEqualTo(5);
        assertThat(mindGame.getNumberOfTries()).isEqualTo(0);
        assertThat(mindGame.isFinished).isEqualTo(false);
    }

    @Test
    void shouldParsePrediction() {
        //given
        String textFromConsole = "blue red white white black";
        String[] expectedResult = new String[]{"blue", "red", "white", "white", "black"};
        String[] result = new String[5];
        //when
        result = mindService.parsePrediction(textFromConsole);
        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckPrediction() {
        //given
        String[] secret = new String[]{"yellow", "red", "white", "black", "red"};
        String[] prediction1 = new String[]{"blue", "red", "white", "white", "black"};
        String[] prediction2 = new String[]{"blue", "blue", "blue", "blue", "blue"};
        String[] prediction3 = new String[]{"red", "yellow", "red", "white", "black"};
        String[] prediction4 = new String[]{"yellow", "red", "white", "black", "red"};

        //when
        int[] result1 = mindService.checkPrediction(prediction1, secret);
        int[] result2 = mindService.checkPrediction(prediction2, secret);
        int[] result3 = mindService.checkPrediction(prediction3, secret);
        int[] result4 = mindService.checkPrediction(prediction4, secret);
        //then
        assertThat(result1).isEqualTo(new int[]{3, 2});
        assertThat(result2).isEqualTo(new int[]{0, 0});
        assertThat(result3).isEqualTo(new int[]{5, 0});
        assertThat(result4).isEqualTo(new int[]{5, 5});
    }

    @Test
    void shouldPlayMindGame() {
        //given
        String[] secret = new String[]{"yellow", "red", "white", "black", "red"};
        MindGame mindGame = new MindGame(secret,0,false);
        when(keyboardInput.getNewInput())
                                        .thenReturn("black blue white black white")
                                        .thenReturn("yellow red white black white")
                                        .thenReturn("yellow red white black red");
        //when
        mindService.playMindGame(mindGame);
        //then
        assertThat(mindGame.isFinished).isTrue();
        assertThat(outputStreamCaptor.toString()).contains("Congratulations, you have guess the combination in 3 tries.");
    }
}