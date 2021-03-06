define([
    "dojo/data/ItemFileWriteStore",
    "dijit/tree/ForestStoreModel",
    "dojox/grid/LazyTreeGrid"
], function (ItemFileWriteStore, ForestStoreModel, LazyTreeGrid) {
    return {
        createInstance: function () {
            var data = {
                identifier: 'name',
                label: 'name',
                items: [
                    {
                        name: 'Africa', type: 'continent', children: [
                        {name: 'Egypt', type: 'country'},
                        {
                            name: 'Kenya', type: 'country', children: [
                            {name: 'Nairobi', type: 'city', adults: 70400, popnum: 2940911},
                            {name: 'Mombasa', type: 'city', adults: 294091, popnum: 707400}]
                        },
                        {
                            name: 'Sudan',
                            type: 'country',
                            children: {name: 'Khartoum', type: 'city', adults: 480293, popnum: 1200394}
                        }]
                    },
                    {
                        name: 'Asia', type: 'continent', children: [
                        {name: 'China', type: 'country'},
                        {name: 'India', type: 'country'},
                        {name: 'Russia', type: 'country', population: '141 million', area: '17 098 242 km²'},
                        {name: 'Mongolia', type: 'country'}]
                    },
                    {
                        name: 'Australia',
                        type: 'continent',
                        population: '21 million',
                        children: {name: 'Commonwealth of Australia', type: 'country', population: '21 million'}
                    },
                    {
                        name: 'Europe', type: 'continent', children: [
                        {name: 'Germany', type: 'country'},
                        {name: 'France', type: 'country'},
                        {name: 'Spain', type: 'country'},
                        {name: 'Italy', type: 'country'}]
                    },
                    {
                        name: 'North America', type: 'continent', children: [
                        {
                            name: 'Mexico',
                            type: 'country',
                            population: '108 million',
                            area: '1,972,550 sq km',
                            children: [
                                {
                                    name: 'Mexico City',
                                    type: 'city',
                                    adults: 120394,
                                    popnum: 19394839,
                                    population: '19 million',
                                    timezone: '-6 UTC'
                                },
                                {
                                    name: 'Guadalajara',
                                    type: 'city',
                                    adults: 1934839,
                                    popnum: 4830293,
                                    population: '4 million',
                                    timezone: '-6 UTC'
                                }]
                        },
                        {
                            name: 'Canada',
                            type: 'country',
                            population: '33 million',
                            area: '9,984,670 sq km',
                            children: [
                                {
                                    name: 'Ottawa',
                                    type: 'city',
                                    adults: 230493,
                                    popnum: 9382019,
                                    population: '0.9 million',
                                    timezone: '-5 UTC'
                                },
                                {
                                    name: 'Toronto',
                                    type: 'city',
                                    adults: 932019,
                                    popnum: 2530493,
                                    population: '2.5 million',
                                    timezone: '-5 UTC'
                                }]
                        },
                        {name: 'United States of America', type: 'country'}]
                    },
                    {
                        name: 'South America', type: 'continent', children: [
                        {name: 'Brazil', type: 'country', population: '186 million'},
                        {name: 'Argentina', type: 'country', population: '40 million'}]
                    }]
            };

            var store = new ItemFileWriteStore({data: data});
            var model = new ForestStoreModel({store: store, childrenAttrs: ['children']});

            /* set up layout */
            var layout = [
                {name: 'Name', field: 'name', width: '30%'},
                {name: 'Type', field: 'type', width: '30%'},
                {name: 'Population', field: 'population', width: '20%'},
                {name: 'Area', field: 'area', width: '20%'}
            ];

            /* create a new grid: */
            var grid = new LazyTreeGrid({
                treeModel: model,
                structure: layout,
                rowSelector: '20px'
            });

            return grid;
        }
    };
});