/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author PC
 */
public class RandomExam {

    public static int randomInt(int min, int max) {
        int random_int = (int) (Math.random() * (max - min + 1) + min);
        return random_int;
    }

}
