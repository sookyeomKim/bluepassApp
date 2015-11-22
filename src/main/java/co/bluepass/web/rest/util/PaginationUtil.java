package co.bluepass.web.rest.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The type Pagination util.
 */
public class PaginationUtil {

    /**
     * The constant DEFAULT_OFFSET.
     */
    public static final int DEFAULT_OFFSET = 1;

    /**
     * The constant MIN_OFFSET.
     */
    public static final int MIN_OFFSET = 1;

    /**
     * The constant DEFAULT_LIMIT.
     */
    public static final int DEFAULT_LIMIT = 20;

    /**
     * The constant MAX_LIMIT.
     */
    public static final int MAX_LIMIT = 100;

    /**
     * Generate page request pageable.
     *
     * @param offset the offset
     * @param limit  the limit
     * @return the pageable
     */
    public static Pageable generatePageRequest(Integer offset, Integer limit) {
    	if(offset == null){
    		limit = DEFAULT_LIMIT;
    	} else if(offset == -1){
    		limit = Integer.MAX_VALUE;
    	} else if (limit > MAX_LIMIT) {
    		limit = DEFAULT_LIMIT;
    	}
        if (offset == null || offset < MIN_OFFSET) {
            offset = DEFAULT_OFFSET;
        }
        return new PageRequest(offset - 1, limit);
    }

    /**
     * Generate page request pageable.
     *
     * @param offset    the offset
     * @param limit     the limit
     * @param sortName  the sort name
     * @param sortOrder the sort order
     * @return the pageable
     */
    public static Pageable generatePageRequest(Integer offset, Integer limit, String sortName, String sortOrder) {
    	if(offset == null){
    		limit = DEFAULT_LIMIT;
    	} else if(offset == -1){
    		limit = Integer.MAX_VALUE;
    	} else if (limit > MAX_LIMIT) {
    		limit = DEFAULT_LIMIT;
    	}
    	if (offset == null || offset < MIN_OFFSET) {
    		offset = DEFAULT_OFFSET;
    	}

    	Sort sort = null;
    	if (StringUtils.isNotEmpty(sortName)){
    		if(StringUtils.isNotEmpty(sortOrder) && "desc".equalsIgnoreCase(sortOrder)){
    			sort = new Sort(Direction.DESC, sortName);
    		}else{
    			sort = new Sort(sortName);
    		}
    	}

    	return new PageRequest(offset -1, limit, sort);
    }

    /**
     * Generate page request pageable.
     *
     * @param offset the offset
     * @param limit  the limit
     * @param sort   the sort
     * @return the pageable
     */
    public static Pageable generatePageRequest(Integer offset, Integer limit, Sort sort) {
    	if(offset == null){
    		limit = DEFAULT_LIMIT;
    	} else if(offset == -1){
    		limit = Integer.MAX_VALUE;
    	} else if (limit > MAX_LIMIT) {
    		limit = DEFAULT_LIMIT;
    	}
    	if (offset == null || offset < MIN_OFFSET) {
    		offset = DEFAULT_OFFSET;
    	}

    	return new PageRequest(offset -1, limit, sort);
    }

    /**
     * Generate pagination http headers http headers.
     *
     * @param page    the page
     * @param baseUrl the base url
     * @param offset  the offset
     * @param limit   the limit
     * @return the http headers
     * @throws URISyntaxException the uri syntax exception
     */
    public static HttpHeaders generatePaginationHttpHeaders(Page page, String baseUrl, Integer offset, Integer limit)
        throws URISyntaxException {

    	if(offset == null){
    		limit = DEFAULT_LIMIT;
    	} else if(offset == -1){
    		limit = Integer.MAX_VALUE;
    	} else if (limit > MAX_LIMIT) {
    		limit = DEFAULT_LIMIT;
    	}
    	if (offset == null || offset < MIN_OFFSET) {
    		offset = DEFAULT_OFFSET;
    	}

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + page.getTotalElements());
        String link = "";
        if (offset < page.getTotalPages()) {
            link = "<" + (new URI(baseUrl +"?page=" + (offset + 1) + "&per_page=" + limit)).toString()
                + ">; rel=\"next\",";
        }
        if (offset > 1) {
            link += "<" + (new URI(baseUrl +"?page=" + (offset - 1) + "&per_page=" + limit)).toString()
                + ">; rel=\"prev\",";
        }
        link += "<" + (new URI(baseUrl +"?page=" + page.getTotalPages() + "&per_page=" + limit)).toString()
            + ">; rel=\"last\"," +
            "<" + (new URI(baseUrl +"?page=" + 1 + "&per_page=" + limit)).toString()
            + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;
    }
}
