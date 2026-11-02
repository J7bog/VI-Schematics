for each process in getObject("winmgmts:\\.\root\cimv2").ExecQuery("SELECT * FROM Win32_Process")
    if process.name = "Mindustry.jar" or process.name = "javaw.exe" and inStr(process.commandLine, "Mindustry.jar") then
        process.terminate()
    end if
next