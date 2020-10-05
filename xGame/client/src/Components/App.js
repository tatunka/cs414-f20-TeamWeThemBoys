import React, {Component} from "react";
import {Collapse, Button} from "reactstrap";

import Login from "./Pages/Login";
import Profile from "./Pages/Profile";
import Match from "./Pages/Match";

import Header from "./Margins/Header";
import Footer from "./Margins/Footer";
import './App.css';

export default class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            showLogin: false,
            showProfile: false,
            showMatch: false
        };
    }

    render() {
        return (
            <div className="App">
                <Header/>
                <Button onClick={() => {this.setState({showLogin:!this.state.showLogin}); console.log(this.state.showLogin)}}>stuff</Button>
                <div className='pages'>
                    {this.renderLogin()}
                    {this.renderProfile()}
                    {this.renderMatch()}
                </div>
                <Footer />
            </div>
        );
    }

    renderLogin(){
        return(
            <Collapse isOpen={this.state.showLogin}>
                <Login />
            </Collapse>
        );
    }

    renderProfile(){
        return(
            <Collapse isOpen={this.state.showProfile}>
                <Profile />
            </Collapse>
        );
    }

    renderMatch(){
        return(
            <Collapse isOpen={this.state.showMatch}>
                <Match />
            </Collapse>
        );
    }
}

