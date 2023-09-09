package view;
import javax.swing.*;
import java.awt.*;
//Класс для взаимодействия пользователя с автоматом. Одна кнопка в окне - при нажатии происходит розыгрыш и
//появляется уведомление о выигранной игрушке, строка записывается в файл
public class MainWin implements View{
    @Override
    public void start() {
        JFrame frame = new JFrame("Автомат с игрушками");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);

        JButton button = new JButton("Хочу игрушку!");
       // button.addActionListener();
        frame.add(button, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    @Override
    public void print(String str) {

    }
}
