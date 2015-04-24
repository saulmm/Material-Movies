import com.hackvg.domain.GetMoviesUsecase;
import com.hackvg.domain.GetMoviesUsecaseController;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.MoviesWrapper;
import com.hackvg.model.rest.RestDataSource;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by saulmm on 18/02/15.
 */
public class GetMoviesUsecaseTest {

    private GetMoviesUsecase getMoviesUsecase;

    @Mock
    private RestDataSource mockRestDataSource;

    @Mock
    private Bus mockUiBus;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        getMoviesUsecase = new GetMoviesUsecaseController(
                mockRestDataSource, mockUiBus);
    }

    @Test
    public void testMoviesRequestExecution() {

        getMoviesUsecase.execute();

        verify(mockRestDataSource, times(1))
            .getMovies();

    }

    @Test
    public void testUiFilmsPost () {


        // Called from rest
        getMoviesUsecase.onPopularMoviesReceived(
            any(MoviesWrapper.class));

        verify(mockUiBus, times(1)).post(
            any(MoviesWrapper.class));
    }
}
