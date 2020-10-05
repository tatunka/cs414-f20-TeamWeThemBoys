import '../enzyme.config.js';
import React from 'react';
import {shallow} from 'enzyme';

import Footer from '../Components/Margins/Footer';

function testInitialState(){
    const app = shallow(<Footer />);
    let expectedState = {
    };
    expect(app.state()).toEqual(expectedState);
}

test("Testing footer initial state", testInitialState);