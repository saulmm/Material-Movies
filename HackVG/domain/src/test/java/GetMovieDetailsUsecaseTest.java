import com.hackvg.domain.GetMovieDetailUsecase;
import com.hackvg.domain.GetMovieDetailUsecaseController;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.MovieDetail;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetMovieDetailsUsecaseTest {

    // Class under test
    private GetMovieDetailUsecase mGetMovieDetailUsecase;

    private String movieTestId = "32";

    @Mock
    private Bus mockUiBus;

    @Mock
    private MediaDataSource mockDataSource;

    @Before
    public void setUp () {

        MockitoAnnotations.initMocks(this);

        mGetMovieDetailUsecase = new GetMovieDetailUsecaseController(
            movieTestId, mockUiBus, mockDataSource);
    }

    @Test
    public void testGetMovieDetailRequestExecution () {

        mGetMovieDetailUsecase.execute();

        verify(mockDataSource, times(1))
            .getDetailMovie(movieTestId);
    }

    @Test
    public void testConfigurationPost () {

        mGetMovieDetailUsecase.onMovieDetailResponse(
            new MovieDetail());

        verify(mockUiBus, times(1)).post(
            any(MovieDetail.class));
    }
}
