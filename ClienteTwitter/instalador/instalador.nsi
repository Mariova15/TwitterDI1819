#idioma español
!include "MUI.nsh"
!insertmacro MUI_LANGUAGE "Spanish"


#iconos del instalado y desinstalador
Icon "Install.ico"
UninstallIcon "UnInstall.ico"

#caracteristicas del instalador
!define PRODUCT_NAME "Cliente de twitter"
!define SETUP_NAME "Cliente de twitter"
!define PRODUCT_VERSION "1.0"
Name "Cliente de twitter"

outFile "Gestion de carreras.exe" 




#carpeta de instalacion por defecto

installDir $PROGRAMFILES\TwitterDI



#permisos de administración

RequestExecutionLevel admin



#pantallas que mostrar al usuario

Page directory

Page instfiles




Section

	#seleccionamos la carpeta de instalación

	SetOutPath $INSTDIR

	

	#escribimos el unistalador

	writeUninstaller "$INSTDIR\uninstall.exe"

	

	#añadimos los archivos que queremos en la instalación

	File /r "..\help"

	File /r "..\informes"
	
	File /r "..\fonts"

	File "..\dist\ClienteTwitter.jar"

	File /r "..\dist\lib"

	

	#creamos los enlaces directos

	CreateDirectory "$SMPROGRAMS\TwitterDI"

	createShortCut "$SMPROGRAMS\TwitterDI\uninstall.lnk" "$INSTDIR\uninstall.exe"

	createShortCut "$SMPROGRAMS\TwitterDI\ClienteTwitter.lnk" "$INSTDIR\ClienteTwitter.jar"

	createShortCut "$DESKTOP\ClienteTwitter.lnk" "$INSTDIR\ClienteTwitter.jar"

 	

	#añadimos la información del registro

	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteTwitter" \
                 "DisplayName" "ClienteTwitter"

	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteTwitter" \
                 "Publisher" "Adan, Shaila, Mario, Sergio - Desarrollo de interfaces"

	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteTwitter" \
                 "UninstallString" "$\"$INSTDIR\uninstall.exe$\""

	

SectionEnd





#definimos el desinstalador

Section "uninstall"

	#borramos el desinstalador

	delete "$INSTDIR\uninstall.exe"

	

	#borramos cada archivo individual

	RmDir /r "$INSTDIR\help"

	RmDir /r "$INSTDIR\informes"

	delete "$INSTDIR\ClienteTwitter.jar"
	
	delete "$INSTDIR\configuracion.cnf"

	RmDir /r "$INSTDIR\lib"
	
	RmDir /r "$INSTDIR\fonts"
	
	RmDir /r "$INSTDIR\imgs"
	RmDir /r "$INSTDIR\sesiones"
	
	

	#borramos los enlaces directos

	delete "$SMPROGRAMS\ClienteTwitter\Gestion.lnk"

	delete "$SMPROGRAMS\ClienteTwitter\uninstall.lnk"

	delete "$DESKTOP\ClienteTwitter.lnk"

	RmDir "$SMPROGRAMS\ClienteTwitter"

	

	#borramos la carpeta de instalación

	RmDir "$INSTDIR"

	#borramos la clave del registro

	DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\ClienteTwitter"

SectionEnd