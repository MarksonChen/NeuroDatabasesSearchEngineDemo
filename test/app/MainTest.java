package app;

import data_access.Database;
import org.junit.jupiter.api.Test;
import view_model.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testAllUseCasesRunsWithoutError(){
        Main.main(null);
        assert true;
    }
}