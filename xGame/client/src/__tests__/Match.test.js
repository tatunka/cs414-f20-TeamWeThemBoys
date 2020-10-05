import '../enzyme.config.js';
import React from 'react';
import {shallow} from 'enzyme';

import Match from '../Components/Pages/Match';

function testInitialState(){
    const app = shallow(<Match />);
    let expectedState = {
    };
    expect(app.state()).toEqual(expectedState);
}

test("Testing login initial state", testInitialState);