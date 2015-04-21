/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propriedades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Propriedades {

    public static Properties getProp() {
        Properties props = new Properties();

        FileInputStream file = null;
        try {
            file = new FileInputStream("C:\\propriedades\\config.properties");
            props.load(file);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Propriedades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Propriedades.class.getName()).log(Level.SEVERE, null, ex);
        }

        return props;
    }

}
