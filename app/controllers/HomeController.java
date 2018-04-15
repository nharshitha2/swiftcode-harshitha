package controllers;

import actors.MessageActor;
import data.LoginForm;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.WebSocket;
import views.html.chat;
import views.html.login;

import javax.inject.Inject;
import java.util.Objects;

public class HomeController extends Controller
{
    public Result chat()
    {
        return ok(chat.render());
    }
    public LegacyWebSocket<String> chatSocket()
    {
        //creates web socket connection,actor with default props and attaches web socket with actor
        return WebSocket.withActor(MessageActor::props);
    }



}
