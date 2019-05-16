/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package key;

/**
 *
 * @author armin
 */
@FunctionalInterface
public interface KeyEventDispatcher {

    public boolean dispatchKeyEvent(KeyEvent e);
}
