package evg299.sample.web.dojo.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Evgeny on 07.01.2015.
 */
public class RestUtil
{
    public static class Range
    {
        private int first;
        private int last;

        public Range(int first, int last)
        {
            this.first = first;
            this.last = last;
        }

        public int getFirst()
        {
            return first;
        }

        public int getLast()
        {
            return last+1;
        }

        @Override
        public String toString()
        {
            return "Range{" +
                    "first=" + first +
                    ", last=" + last +
                    '}';
        }
    }

    public static class SortContainer
    {
        private String field;
        private boolean asc;

        public SortContainer(String field, boolean asc)
        {
            this.field = field;
            this.asc = asc;
        }

        public String getField()
        {
            return field;
        }

        public boolean isAsc()
        {
            return asc;
        }

        @Override
        public String toString()
        {
            return "SortContainer{" +
                    "field='" + field + '\'' +
                    ", asc=" + asc +
                    '}';
        }
    }

    public static class FilterContainer
    {
        private String field;
        private String value;

        public FilterContainer(String field, String value)
        {
            this.field = field;
            this.value = value;
        }

        public String getField()
        {
            return field;
        }

        public String getValue()
        {
            return value;
        }

        @Override
        public String toString()
        {
            return "FilterContainer{" +
                    "field='" + field + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static class FilterAndSortContainer
    {
        private List<SortContainer> sort = new ArrayList<SortContainer>();
        private List<FilterContainer> filter = new ArrayList<FilterContainer>();

        public List<SortContainer> getSort()
        {
            return sort;
        }

        public List<FilterContainer> getFilter()
        {
            return filter;
        }
    }

    private static final String SORT_PREFIX = "sort(";

    private static final String RANGE_PREFIX = "items=";

    public static Range parseRange(String rangeString)
    {
        if(null == rangeString)
            return null;

        if(rangeString.startsWith(RANGE_PREFIX))
        {
            String tmp = rangeString.substring(RANGE_PREFIX.length());
            String[] fl = tmp.split("-");
            if(2 == fl.length)
                return new Range(Integer.parseInt(fl[0]), Integer.parseInt(fl[1]));
            if(1 == fl.length)
                return new Range(Integer.parseInt(fl[0]), -1);
        }

        return null;
    }

    public static List<SortContainer> parseSortContainers(String sort)
    {
        // sort( email,-birthDate)
        List<SortContainer> result = new ArrayList<SortContainer>();
        if(null == sort)
            return result;

        if(sort.startsWith(SORT_PREFIX))
        {
            String tmp = sort.substring(SORT_PREFIX.length());
            tmp = tmp.substring(0, tmp.length()-1);
            String[] fields = tmp.split(",");
            for(String field: fields)
            {
                boolean asc = true;
                if(field.startsWith("-"))
                    asc = false;

                result.add(new SortContainer(field.substring(1), asc));
            }
        }

        return result;
    }

    public static FilterAndSortContainer parseFilterAndSort(HttpServletRequest request)
    {
        FilterAndSortContainer result = new FilterAndSortContainer();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements())
        {
            String paramName = parameterNames.nextElement();
            if(paramName.startsWith(SORT_PREFIX))
            {
                result.getSort().addAll(parseSortContainers(paramName));
            }
            else
            {
                String[] paramValues = request.getParameterValues(paramName);
                String paramValue = null;
                if(0 < paramValues.length)
                    paramValue = paramValues[0];

                result.getFilter().add(new FilterContainer(paramName, paramValue));
            }
        }

        return result;
    }
}
