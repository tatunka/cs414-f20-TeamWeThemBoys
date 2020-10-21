import {findLocation} from '../HelpfulFunction.js'

function testFindLocation (){
    let testLocation = 'a1';
    let expectedResult = [7,7];
    expect(findLocation(testLocation).toEqual(expectedResult)
}

test("Testing find location", testFindLocation);