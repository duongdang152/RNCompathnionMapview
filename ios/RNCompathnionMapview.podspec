
require 'json'


Pod::Spec.new do |s|
  s.name         = "RNCompathnionMapview"
  s.version      = "1.0.0"
  s.summary      = "RNCompathnionMapview"
  s.description  = <<-DESC
                  RNCompathnionMapview
                   DESC
  s.homepage     = "https://gitpub.compathnion.com/"
  s.license      = "MIT"
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/duongdang152/RNCompathnionMapview.git", :tag => "iosMapModule" }
  s.source_files  = "ios/RNCompathnion/**/*.{h,m}"
  s.requires_arc = true

  s.dependency 'Mapbox-iOS-SDK', '~> 5.4.0'
  s.dependency "React"
  #s.dependency "others"

end

  